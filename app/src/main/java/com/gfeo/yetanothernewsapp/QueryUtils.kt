package com.gfeo.yetanothernewsapp

import android.net.Uri
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.Charset
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.HttpsURLConnection

class QueryUtils private constructor() {

    companion object {

        private val LOG_TAG: String = QueryUtils::class.simpleName!!
        private const val PAGE_SIZE_VALUE = "20"

        @JvmStatic
        fun buildQueryUrl(sectionId: String): URL? {
            val uriBuilder: Uri.Builder = Uri.Builder()
            uriBuilder.scheme("https")
                    .authority("content.guardianapis.com")
                    .appendPath("search")
                    .appendQueryParameter("show-fields", "headline,byline")
                    .appendQueryParameter("page-size", PAGE_SIZE_VALUE)
                    .appendQueryParameter("api-key", "test");
            if (!sectionId.isEmpty()) uriBuilder.appendQueryParameter("section", sectionId)
            return createUrl(uriBuilder.toString())
        }

        private fun createUrl(stringUrl: String): URL? {
            var url: URL? = null
            try {
                url = URL(stringUrl)
            } catch (exception: MalformedURLException) {
                Log.e(LOG_TAG, "Error creating URL ", exception)
            }
            return url
        }

        private fun parseUtcDate(utcDateString: String): String {
            var dateString = utcDateString
            val utcDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            utcDateFormat.timeZone = TimeZone.getTimeZone("UTC")
            try {
                val date: Date = utcDateFormat.parse(dateString)
                val storyDateFormat: SimpleDateFormat = SimpleDateFormat("EEE, MMM dd, HH:mm")
                storyDateFormat.timeZone = TimeZone.getDefault();
                dateString = storyDateFormat.format(date)
            } catch (exception: ParseException) {
                Log.e(LOG_TAG, "Error parsing dateTime String", exception)
            } catch (exception: IllegalArgumentException) {
                Log.e(LOG_TAG, "Error formatting dateTime String", exception)
            }
            return dateString
        }

        @JvmStatic
        fun makeHttpRequest(url: URL?): String {
            var jsonResponse = ""
            if (url == null) return jsonResponse

            var urlConnection: HttpsURLConnection? = null
            var inputStream: InputStream? = null
            try {
                urlConnection = url.openConnection() as HttpsURLConnection
                urlConnection.readTimeout = 10000
                urlConnection.connectTimeout = 15000
                urlConnection.requestMethod = "GET"
                urlConnection.connect()

                if (urlConnection.responseCode == HttpsURLConnection.HTTP_OK) {
                    inputStream = urlConnection.inputStream
                    jsonResponse = readFromStream(inputStream)
                } else {
                    Log.e(LOG_TAG, "Error response code: ${urlConnection.responseCode}")
                }
            } catch (exception: IOException) {
                Log.e(LOG_TAG, "Problem retrieving the JSON results.", exception)
            } finally {
                urlConnection?.disconnect()
                try {
                    inputStream?.close()
                } catch (exception: IOException) {
                    Log.e(LOG_TAG, "IOException thrown when closing the input stream", exception)
                }
            }
            return jsonResponse
        }

        private fun readFromStream(inputStream: InputStream): String {
            val output = StringBuilder()
            val inputStreamReader = InputStreamReader(inputStream, Charset.forName("UTF-8"))
            val reader = BufferedReader(inputStreamReader)
            //TODO test with not null String
            var line: String? = reader.readLine() //TODO test kotlin.io readLine()
            while (line != null) {
                output.append(line)
                line = reader.readLine()
            }
            return output.toString()
        }

        @JvmStatic
        fun parseJsonToArrayList(jsonResponseString: String,
                                 storyArrayList: ArrayList<Story>): ArrayList<Story> {

            var storyHeadline = ""
            var storyDateTime = ""
            var storySection = ""
            var storyAuthor = ""
            var storyLink = ""

            if (!storyArrayList.isEmpty()) storyArrayList.clear()

            try {
                val jsonResponse = JSONObject(jsonResponseString).getJSONObject("response")
                val resultsJsonArray = jsonResponse.getJSONArray("results")

                var currentIteration = 0
                while (currentIteration < Integer.valueOf(PAGE_SIZE_VALUE)){
                    val currentResult: JSONObject = resultsJsonArray.getJSONObject(currentIteration)
                    val requestedFields: JSONObject = currentResult.getJSONObject("fields")

                    //Get the story headline
                    storyHeadline = requestedFields.optString("headline")

                    //Try getting the story date and time
                    storyDateTime = currentResult.optString("webPublicationDate")
                    //storyDateTime = parseUtcDate(storyDateTime)

                    //Get the story author name (if available)
                    storyAuthor = requestedFields.optString("byline")

                    //Get the section name
                    storySection = currentResult.optString("sectionName")

                    //Get the link to the story
                    storyLink = currentResult.optString("webUrl")

                    //Add current Story object
                    storyArrayList.add(Story(storyHeadline,
                            storyDateTime,
                            storyAuthor,
                            storySection,
                            storyLink))
                    currentIteration++
                }
            } catch (exception: JSONException) {
                Log.e(LOG_TAG, exception.message)
            }

            return storyArrayList
        }

    }
}