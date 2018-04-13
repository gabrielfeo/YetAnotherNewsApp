package com.gfeo.yetanothernewsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by gabrielfeo on 2018/04/13.
 */

class QueryUtilsPrefs {

	static int numberOfResults;
	static Integer maxResultsValue;
	private static String maxResultsKey;
	private static String orderByKey;
	private static String orderByValue;
	private static String langRestrictKey;
	private static String langRestrictValue;

	//TODO Adapt preferences
	static void getPreferences(Context context) {
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

		//maxResults setting
		maxResultsKey = context.getString(R.string.preferences_maxresults_key);
		String maxResultsDefault = context.getString(R.string.preferences_maxresults_default);
		String maxResultsValueString = sharedPrefs.getString(maxResultsKey, maxResultsDefault);
		try {
			int mMaxResults = Integer.parseInt(maxResultsValueString);
			maxResultsValue = (mMaxResults >= 1 && mMaxResults <= 40)
			                  ? mMaxResults
			                  : Integer.parseInt(maxResultsDefault);
		} catch (NumberFormatException e) {
			Log.e(LOG_TAG, e.getMessage());
			maxResultsValue = Integer.parseInt(maxResultsDefault);
		}

		//orderBy setting
		orderByKey = context.getString(R.string.preferences_orderby_key);
		String orderByDefault = context.getString(R.string.preferences_orderby_default);
		orderByValue = sharedPrefs.getString(orderByKey, orderByDefault);

		//langRestrict setting
		langRestrictKey = context.getString(R.string.preferences_langrestrict_key);
		String langRestrictDefault = context.getString(R.string.preferences_langrestrict_default)
		                                    .toLowerCase();
		langRestrictValue = sharedPrefs.getString(langRestrictKey, langRestrictDefault);

	}

}
