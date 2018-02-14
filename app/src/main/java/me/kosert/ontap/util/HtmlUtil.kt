package me.kosert.ontap.util

import android.graphics.Color
import android.text.Html
import android.text.Spanned

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

fun String.fromHtml() : Spanned
{
	return (if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
		Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
	}
	else {
		Html.fromHtml(this)
	})
}

fun String.toColor() : Int?
{
	return try
	{
		val c = Color.parseColor(this)
		if (c != Color.GRAY) c
		else null
	}
	catch (e: Exception) { null }
}
