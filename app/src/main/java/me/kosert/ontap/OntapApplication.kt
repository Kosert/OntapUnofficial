package me.kosert.ontap

import android.app.Application
import android.content.Context
import me.kosert.ontap.data.StaticProvider

/**
 * Created by Kosert on 2018-02-10.
 */

class OntapApplication : Application()
{
	override fun onCreate()
	{
		super.onCreate()
		val memory = getSharedPreferences(getString(R.string.memory_key), Context.MODE_PRIVATE)
		val prefs = getSharedPreferences(getString(R.string.preference_key), Context.MODE_PRIVATE)
		StaticProvider.initialize(memory, prefs)
		//StaticProvider.Memory.resetMemory()
	}
}