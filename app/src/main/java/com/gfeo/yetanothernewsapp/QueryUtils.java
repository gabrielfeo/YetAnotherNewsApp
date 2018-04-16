package com.gfeo.yetanothernewsapp;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;


class QueryUtils {

	private final static String LOG_TAG = QueryUtils.class.getSimpleName();
	private final static String pageSizeValue = "20";

	static URL buildQueryUrl(String sectionId) {
		Uri.Builder uriBuilder = new Uri.Builder();
		uriBuilder.scheme("https")
		          .authority("content.guardianapis.com")
		          .appendPath("search")
		          .appendQueryParameter("show-fields", "headline,byline")
		          .appendQueryParameter("page-size", pageSizeValue)
		          .appendQueryParameter("api-key", "test");
		if (!sectionId.isEmpty()) {
			uriBuilder.appendQueryParameter("section", sectionId);
		}
		return createUrl(uriBuilder.toString());
	}

	private static URL createUrl(String stringUrl) {
		URL url = null;
		try {
			url = new URL(stringUrl);
		} catch (MalformedURLException e) {
			Log.e(LOG_TAG, "Error creating URL ", e);
		}
		return url;
	}

	private static String parseUtcDate(String utcDateString) {
		String dateString = utcDateString;
		@SuppressLint("SimpleDateFormat") DateFormat utcDateFormat = new SimpleDateFormat
				("yyyy-MM-dd'T'HH:mm:ss'Z'");
		utcDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			Date date = utcDateFormat.parse(dateString);
			@SuppressLint("SimpleDateFormat") DateFormat storyDateFormat = new SimpleDateFormat
					("EEE, MMM dd, HH:mm");
			storyDateFormat.setTimeZone(TimeZone.getDefault());
			dateString = storyDateFormat.format(date);
		} catch (ParseException e) {
			Log.e(LOG_TAG, "Error parsing dateTime String", e);
		} catch (IllegalArgumentException e) {
			Log.e(LOG_TAG, "Error formatting dateTime String", e);
		}
		return dateString;
	}

	static String makeHttpRequest(URL url) {
		String jsonResponse = "";

		// If the URL is null, then return early.
		if (url == null) {
			return jsonResponse;
		}

		HttpURLConnection urlConnection = null;
		InputStream inputStream = null;
		try {
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setReadTimeout(10000);
			urlConnection.setConnectTimeout(15000);
			urlConnection.setRequestMethod("GET");
			urlConnection.connect();

			int httpResponseCode = urlConnection.getResponseCode();
			if (httpResponseCode == 200) {
				inputStream = urlConnection.getInputStream();
				jsonResponse = readFromStream(inputStream);
			} else {
				Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
			}
		} catch (IOException e) {
			Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					Log.e(LOG_TAG, "IOException thrown when closing the input stream", e);
				}
			}
		}
		return jsonResponse;
	}


	private static String readFromStream(InputStream inputStream) throws IOException {
		StringBuilder output = new StringBuilder();
		if (inputStream != null) {
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset
					.forName("UTF-8"));
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String line = reader.readLine();
			while (line != null) {
				output.append(line);
				line = reader.readLine();
			}
		}
		return output.toString();
	}


	static ArrayList<Story> parseJsonToArrayList(String jsonResponseString,
	                                             ArrayList<Story> storyArrayList) {

		String storyHeadline = "";
		String storyDateTime = "";
		String storySection = "";
		String storyAuthor = "";
		String storyLink = "";

		if (!storyArrayList.isEmpty()) {
			storyArrayList.clear();
		}

		try {
			JSONObject jsonResponse = new JSONObject(jsonResponseString).getJSONObject("response");
			JSONArray resultsArray = jsonResponse.getJSONArray("results");

			for (int i = 0; i < Integer.valueOf(pageSizeValue); i++) {
				JSONObject currentResult = resultsArray.getJSONObject(i);
				JSONObject requestedFields = currentResult.getJSONObject("fields");

				//Get the story headline
				storyHeadline = requestedFields.getString("headline");

				//Try getting the story date and time
				try {
					storyDateTime = currentResult.getString("webPublicationDate");
					storyDateTime = parseUtcDate(storyDateTime);
				} catch (JSONException e) {
					checkForPermittedJsonException(e);
				}

				//Get the story author name (if available)
				try {
					storyAuthor = requestedFields.getString("byline");
				} catch (JSONException e) {
					checkForPermittedJsonException(e);
				}

				//Get the section name
				try {
					storySection = currentResult.getString("sectionName");
				} catch (JSONException e) {
					checkForPermittedJsonException(e);
				}

				//Get the link to the story
				try {
					storyLink = currentResult.getString("webUrl");
				} catch (JSONException e) {
					checkForPermittedJsonException(e);
				}

				//Add current Story object
				storyArrayList.add(new Story(storyHeadline,
				                             storyDateTime,
				                             storyAuthor,
				                             storySection,
				                             storyLink)
				                  );

			}
		} catch (JSONException e) {
			Log.e(LOG_TAG, e.getMessage());
		}

		return storyArrayList;
	}

	private static void checkForPermittedJsonException(JSONException e) throws JSONException {
		String permittedExceptionKeyphrase = "No value for";
		if (!e.getMessage().contains(permittedExceptionKeyphrase)) {
			throw new JSONException(e.getMessage());
		}
	}

}
