package me.kosert.ontap.util

import android.util.Log

/**
 * Created by Kosert on 2018-02-10.
 */

class Logger(private val tag : String)
{
	companion object
	{
		private val universalTag = "LOG"

		fun list(list: List<Any>)
		{
			list.forEach {
				Log.d(universalTag, it.toString())
			}
		}

		fun d(message: String)
		{
			Log.d(universalTag, message)
		}
	}

	fun list(list: List<Any>)
	{
		list.forEach {
			Log.d(tag, it.toString())
		}
	}

	fun i(message: String)
	{
		Log.i(tag, message)
	}

	fun w(message: String)
	{
		Log.w(tag, message)
	}

	fun e(message: String)
	{
		Log.e(tag, message)
	}
}