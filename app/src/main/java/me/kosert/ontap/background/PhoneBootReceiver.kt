package me.kosert.ontap.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import me.kosert.ontap.util.NotificationUtil

/**
 * Created by Kosert on 2018-02-18.
 */
class PhoneBootReceiver : BroadcastReceiver()
{
	override fun onReceive(context: Context, intent: Intent?)
	{
		NotificationUtil.scheduleJob(context)
	}
}