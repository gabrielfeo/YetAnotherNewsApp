package com.gfeo.yetanothernewsapp;

import android.net.Uri;

/**
 * Represents a news story. Its state consists of the headline, date and time of publication, the
 * section it belongs to, the author name, as well as the link to story on the website.
 *
 * @author gabrielfeo
 */

class Story {

	/** A String holding the story headline */
	private final String mHeadline;
	/** A String holding the date and time the story was published */
	private final String mDateTime;
	/** A String holding the story author name */
	private final String mAuthor;
	/** A String holding the name of the section the story belongs to */
	private final String mSection;
	/** A String holding a Uri to the story's page on the website */
	private final Uri mLink;

	/**
	 * Sets the basic information about the story.
	 *
	 * @param headline A String holding the story headline
	 * @param dateTime A String holding the date and time the story was published
	 * @param author   A String holding the story author name
	 * @param section  A String holding the name of the section the story belongs to
	 * @param link     A String holding a Uri to the story's page on the website
	 */
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
