package me.kosert.ontap.util

import android.util.Log
import me.kosert.ontap.data.StaticProvider

/**
 * Created by Kosert on 2018-02-10.
 */

class Logger(logTag : String)
{
	companion object
	{
		private const val universalTag = "OntapUnofficial"

		fun list(list: List<Any>)
		{
			if (StaticProvider.DEBUG) return

			list.forEach {
				Log.d(universalTag, it.toString())
			}
		}

		fun d(message: String)
		{
			if (StaticProvider.DEBUG) return

			Log.d(universalTag, message)
		}
	}

	private val tag : String = "OntapUnofficial." + logTag

	fun list(list: List<Any>)
	{
		if (StaticProvider.DEBUG) return

		list.forEach {
			Log.d(tag, it.toString())
		}
	}

	fun i(message: String)
	{
		if (StaticProvider.DEBUG) return

		Log.i(tag, message)
	}

	fun w(message: String)
	{
		if (StaticProvider.DEBUG) return

		Log.w(tag, message)
	}

	fun e(message: String)
	{
		if (StaticProvider.DEBUG) return

		Log.e(tag, message)
	}
}