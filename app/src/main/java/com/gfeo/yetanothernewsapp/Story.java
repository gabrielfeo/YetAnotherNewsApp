package com.gfeo.yetanothernewsapp;

/**
 * Created by gabrielfeo on 2018/04/11.
 */

class Story {

	private String mTitle;
	private String mSubtitle;
	private String mSnippet;
	private String mDateTime;
	private String mSection;
	private String mAuthor;

	Story(String title, String subtitle, String snippet, String dateTime, String author,
	      String section) {
		mTitle = title;
		mSubtitle = subtitle;
		mSnippet = snippet;
		mDateTime = dateTime;
		mAuthor = author;
		mSection = section;
	}

	String getTitle() {
		return mTitle;
	}

	String getSubtitle() {
		return mSubtitle;
	}

	String getSnippet() {
		return mSnippet;
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
