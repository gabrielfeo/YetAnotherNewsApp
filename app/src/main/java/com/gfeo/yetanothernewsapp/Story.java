package com.gfeo.yetanothernewsapp;

import android.net.Uri;

/**
 * Created by gabrielfeo on 2018/04/11.
 */

class Story {

	private String mHeadline;
	private String mDateTime;
	private String mSection;
	private String mAuthor;
	private Uri mLink;

	Story(String headline, String dateTime, String author, String section, String link) {
		mHeadline = headline;
		mDateTime = dateTime;
		mAuthor = author;
		mSection = section;
		mLink = Uri.parse(link);
	}

	String getTitle() {
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
