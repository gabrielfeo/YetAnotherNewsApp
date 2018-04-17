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

/**
 * Utility class for querying <a href="http://open-platform.theguardian.com/">The Guardian's
 * <i>Open Platform API</i></a>.
 *
 * @author gabrielfeo
 */
class QueryUtils {

	/** A String containing the simple class name for {@link Log} methods */
	private final static String LOG_TAG = QueryUtils.class.getSimpleName();
	/**
	 * A String setting a maximum number of news stories for the API to return
	 *
	 * @see #buildQueryUrl(String)
	 */
	private final static String pageSizeValue = "20";

	/**
	 * <p>Builds the query URL using a {@link Uri.Builder} and the
	 * {@link #createUrl(String)} method.</p>
	 * <p>The {@code Uri.Builder} appends query parameters that request the story headline and
	 * byline specifically, and sets the <i>page-size</i> parameter according to the
	 * {@link #pageSizeValue} field (this is the limit number of news stories to be
	 * returned from the API).</p>
	 *
	 * @param sectionId the section ID value for the <i>section</i> parameter. This differs from
	 *                  the <i>section name</i>; the ID of the Opinion section, for instance,
	 *                  is "commentisfree".
	 * @return the built query {@link URL}
	 */
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

	/**
	 * Creates a new URL object from the given String URL.
	 *
	 * @param stringUrl a String holding a URL address
	 * @return the created URL object
	 */
	private static URL createUrl(String stringUrl) {
		URL url = null;
		try {
			url = new URL(stringUrl);
		} catch (MalformedURLException e) {
			Log.e(LOG_TAG, "Error creating URL ", e);
		}
		return url;
	}

	/**
	 * Parses the UTC-format date and time String from the argument and reformats it to a custom
	 * scheme, also converting it to the device's default time zone.
	 *
	 * @param utcDateString a String containing a UTC-formatted date and time
	 * @return the reformatted and converted date and time String
	 */
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

	/**
	 * Makes an HTTP request to the given URL and returns a String as the response.
	 *
	 * @param url the URL for the HTTP request
	 * @return a String of the JSON HTTP response
	 */
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

	/**
	 * Converts the {@link InputStream} into a String which contains the
	 * whole JSON response from the server.
	 *
	 * @param inputStream the {@code InputStream} to be read
	 * @return the {@link BufferedReader} output from reading the
	 * {@code InputStream}
	 */
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

	/**
	 * <p>Parses the JSON response returned by the
	 * <a href="http://open-platform.theguardian.com/">The Guardian's <i>Open Platform
	 * API</i></a> to an {@link ArrayList} of {@link Story} objects.</p>
	 * <p>First, the {@code ArrayList} is cleared (if not empty already), so that the list items
	 * aren't duplicated when this method is called on a list refresh. Then, a loop is started
	 * and iterated according to the {@link #pageSizeValue} field, thus precisely to the number
	 * of stories in the response. The loop retrieves Strings from the JSON containing the story
	 * headline, author, publication date and time, section name, and a link to the story page on
	 * the website, namely, the fields of a {@code Story} object. Finally, each retrieved value,
	 * empty or not, is used as an argument to the
	 * {@link Story#Story(String, String, String, String, String)} constructor.</p>
	 *
	 * @param jsonResponseString the JSON response to be parsed, in a String object
	 * @param storyArrayList     the {@code ArrayList<Story>} the JSON will be parsed to
	 * @return the fully populated {@code ArrayList<Story>}
	 */
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

	/**
	 * Used in the first line in a catch block to check the exception argument message for a
	 * certain keyphrase. If the exception corresponds to the permitted "No value for [{@code
	 * key}]" {@link JSONException}, the exception will be disregarded and the caller method can
	 * resume execution. Otherwise, another exception will be thrown.
	 *
	 * @param e the {@code JSONException} to be checked
	 * @throws JSONException if the {@code JSONException} argument isn't of the allowed type
	 */
	private static void checkForPermittedJsonException(JSONException e) throws JSONException {
		String permittedExceptionKeyphrase = "No value for";
		if (!e.getMessage().contains(permittedExceptionKeyphrase)) {
			throw new JSONException(e.getMessage());
		}
	}

}
