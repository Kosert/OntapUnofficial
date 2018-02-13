package me.kosert.ontap.util

import android.text.Html

/**
 * Created by Kosert on 2018-02-13.
 */

/**
 *	Replaces HTML entities with characters.
 */
fun String.stripHtml(): String
{
	return (if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
		Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
	}
	else {
		Html.fromHtml(this)
	}).toString()
}
