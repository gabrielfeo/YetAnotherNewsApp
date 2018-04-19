package com.gfeo.yetanothernewsapp

import android.net.Uri

/**
 * Represents a news story. Its state consists of the [headline], [dateTime] of publication,
 * the [section] it belongs to, the [author] name, as well as the [link] to story on the website.
 *
 * @author gabrielfeo
 */

data class Story(val headline: String,
                 val dateTime: String,
                 val author: String,
                 val section: String,
                 private val linkString: String) {
    val link: Uri = Uri.parse(linkString);
}