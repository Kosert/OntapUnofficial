package me.kosert.ontap

import android.app.Application
import android.content.Context
import me.kosert.ontap.data.StaticProvider

/**
 * Created by Kosert on 2018-02-10.
 */

class OntapApplication : Application()
{
	/*companion object
	{
		private var appContext : Context? = null

		fun getContext(): Context?
		{
			return appContext
		}
	}*/

	override fun onCreate()
	{
		super.onCreate()
		val prefs = getSharedPreferences(getString(R.string.preference_key), Context.MODE_PRIVATE)
		StaticProvider.initialize(prefs)
	}
}