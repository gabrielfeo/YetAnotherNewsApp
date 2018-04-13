package com.gfeo.yetanothernewsapp;

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
import java.util.ArrayList;


class QueryUtils {

	private final static String LOG_TAG = QueryUtils.class.getSimpleName();
	static int numberOfResults;

	//TODO Change to Guardian URL
	static URL buildQueryUrl(String searchQuery) {
		Uri.Builder uriBuilder = new Uri.Builder();
		uriBuilder.scheme("https")
		          .authority("content.guardianapis.com")
		          .appendPath("search")
		          .appendQueryParameter("show-fields", "headline%2Cbyline")
		          .appendQueryParameter("page-size", "20")
		          .appendQueryParameter("api-key", "test");
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

		//TODO Adapt JSON parser for the Guardian JSON response
		try {
			JSONObject jsonObject = new JSONObject(jsonResponseString);
			numberOfResults = jsonObject.getInt("totalItems");
			JSONArray itemsArray = jsonObject.getJSONArray("items");
			//TODO maxiterations
			for (int i = 0; i < 10; i++) {
				JSONObject volumeInfo = itemsArray.getJSONObject(i)
				                                  .getJSONObject("volumeInfo");

				//Get the story headline
				storyHeadline = volumeInfo.getString("title");

				//Try getting the story date and time
				try {
					storyDateTime = volumeInfo.getString("publishedDate")
					                          .substring(0, 4);
				} catch (JSONException e) {
					//TODO Is this necessary?
					checkForPermittedJsonException(e);
					storyDateTime = "No date available";
				}

				//Get the story author name (if available)
				try {
					storyAuthor = "";
				} catch (JSONException e) {
					checkForPermittedJsonException(e);
					storyAuthor = "Author name not available";
				}

				//Try getting the link to the story
				try {
					storyLink = volumeInfo.getJSONObject("imageLinks")
					                      .getString("smallThumbnail");
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
