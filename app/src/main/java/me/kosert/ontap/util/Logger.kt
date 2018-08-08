package me.kosert.ontap.util

import android.util.Log
import me.kosert.ontap.data.StaticProvider

/**
 * Class used to write messages to Logcat.
 * Created by Kosert on 2018-02-10.
 * @constructor
 * Class used to write messages to Logcat.
 * Created by Kosert on 2018-02-10.
 * @param logTag
 * Tag that will be used for [Log] method calls by this instance
 */
class Logger(logTag : String)
{
	/**
	 * Static object for writing debug log messages
	 */
	companion object
	{
		private const val universalTag = "OntapUnofficial"

		init
		{
			Log.d(universalTag, "DEBUG MODE IS " + if(StaticProvider.DEBUG) "ON" else "OFF")
		}

		fun list(list: List<Any>)
		{
			if (!StaticProvider.DEBUG) return

			list.forEachIndexed { index, any ->
				Log.d(universalTag, index.toString() + ": " + any.toString())
			}
		}

		fun d(message: String)
		{
			if (!StaticProvider.DEBUG) return

			Log.d(universalTag, message)
		}
	}

	private val tag : String = "OntapUnofficial." + logTag

	fun list(list: List<Any>)
	{
		if (!StaticProvider.DEBUG) return

		list.forEachIndexed { index, any ->
			Log.d(tag, index.toString() + ": " + any.toString())
		}
	}

	fun i(message: String)
	{
		if (!StaticProvider.DEBUG) return

		Log.i(tag, message)
	}

	fun w(message: String)
	{
		if (!StaticProvider.DEBUG) return

		Log.w(tag, message)
	}

	fun e(message: String)
	{
		if (!StaticProvider.DEBUG) return

		Log.e(tag, message)
	}
}