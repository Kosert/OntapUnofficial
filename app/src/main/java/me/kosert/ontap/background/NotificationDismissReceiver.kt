package me.kosert.ontap.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import me.kosert.ontap.data.StaticProvider
import me.kosert.ontap.model.Multitap
import me.kosert.ontap.util.Logger

/**
 * Created by Kosert on 2018-02-21.
 */

const val MULTITAP_JSON_EXTRA = "MULTITAP_JSON_EXTRA"

class NotificationDismissReceiver : BroadcastReceiver()
{
	override fun onReceive(context: Context, intent: Intent)
	{
		val multitapJson = intent.getStringExtra(MULTITAP_JSON_EXTRA)
		val multitap = StaticProvider.getGson().fromJson(multitapJson, Multitap::class.java)

		Logger.d("NOTIFICATION DISMISSED")
		StaticProvider.NotificationMemory.removeNotReadFlag(multitap)
	}
}