package com.gfeo.yetanothernewsapp;

/**
 * Created by gabrielfeo on 2018/04/11.
 */

class Story {

	private String mHeadline;
	private String mDateTime;
	private String mSection;
	private String mAuthor;

	Story(String headline, String dateTime, String author, String section) {
		mHeadline = headline;
		mDateTime = dateTime;
		mAuthor = author;
		mSection = section;
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
}
