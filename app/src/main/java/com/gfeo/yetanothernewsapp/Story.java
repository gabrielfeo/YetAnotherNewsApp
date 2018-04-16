package com.gfeo.yetanothernewsapp;

import android.net.Uri;

/**
 * Created by gabrielfeo on 2018/04/11.
 */

class Story {

	private final String mHeadline;
	private final String mDateTime;
	private final String mSection;
	private final String mAuthor;
	private final Uri mLink;

	Story(String headline, String dateTime, String author, String section, String link) {
		mHeadline = headline;
		mDateTime = dateTime;
		mAuthor = author;
		mSection = section;
		mLink = Uri.parse(link);
	}

	String getHeadline() {
		return mHeadline;
	}

	String getDateTime() {
		return mDateTime;
	}

	String getSection() {
		return mSection;
	}

	String getAuthor() {
		return mAuthor;
	}

	Uri getLink() {
		return mLink;
	}
}
