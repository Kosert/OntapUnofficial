package me.kosert.ontap.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import me.kosert.ontap.R
import me.kosert.ontap.data.StaticProvider
import me.kosert.ontap.util.BackgroundUtil

/**
 * Created by Kosert on 2018-02-18.
 */
class PhoneBootReceiver : BroadcastReceiver()
{
	override fun onReceive(context: Context, intent: Intent?)
	{
		if(StaticProvider.isPrefsNotInitialized())
		{
			val prefs = context.getSharedPreferences(context.getString(R.string.preference_key), Context.MODE_PRIVATE)
			StaticProvider.initializePrefs(prefs)
		}

		if (StaticProvider.Prefs.getPrefBoolean(StaticProvider.Prefs.PrefType.NOTIFICATIONS_KEY))
			BackgroundUtil.scheduleJob(context)
	}
}