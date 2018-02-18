package me.kosert.ontap.ui.activities.settings

import android.content.Context
import android.content.Intent
import android.widget.Toast
import me.kosert.ontap.R
import me.kosert.ontap.data.StaticProvider
import me.kosert.ontap.model.Multitap
import me.kosert.ontap.ui.activities.about.AboutActivity
import me.kosert.ontap.ui.activities.settings.adapters.RecyclerNotificationAdapter

/**
 * Created by Kosert on 2018-02-16.
 */
class SettingsController
{
	lateinit var context: Context
	lateinit var callbacks: ISettingsCallbacks

	fun onCreate(context: Context, callbacks: ISettingsCallbacks)
	{
		this.context = context
		this.callbacks = callbacks

		val recyclerCallbacks = object : RecyclerNotificationAdapter.ItemClickCallback
		{
			override fun onItemClicked(multitap: Multitap)
			{
				if (StaticProvider.NotificationMemory.isNotificationEnabled(multitap))
				{
					StaticProvider.NotificationMemory.removeNotification(multitap)
					callbacks.notifyRecycler()

				}
				else
				{
					StaticProvider.NotificationMemory.addNotification(multitap)
					callbacks.notifyRecycler()
				}
			}
		}

		callbacks.setRecyclerCallback(recyclerCallbacks)

		val notify = StaticProvider.Prefs.getPrefBoolean(StaticProvider.Prefs.PrefType.NOTIFICATIONS_KEY)
		val sound = StaticProvider.Prefs.getPrefBoolean(StaticProvider.Prefs.PrefType.SOUND_KEY)
		val vibrate = StaticProvider.Prefs.getPrefBoolean(StaticProvider.Prefs.PrefType.VIBRATE_KEY)
		val led = StaticProvider.Prefs.getPrefBoolean(StaticProvider.Prefs.PrefType.LED_KEY)

		callbacks.setPrefs(notify, sound, vibrate, led)
	}

	fun enableNotifications(checked: Boolean)
	{
		StaticProvider.Prefs.setPrefBoolean(StaticProvider.Prefs.PrefType.NOTIFICATIONS_KEY, checked)
	}

	fun enableSound(checked: Boolean)
	{
		StaticProvider.Prefs.setPrefBoolean(StaticProvider.Prefs.PrefType.SOUND_KEY, checked)
	}

	fun enableVibrate(checked: Boolean)
	{
		StaticProvider.Prefs.setPrefBoolean(StaticProvider.Prefs.PrefType.VIBRATE_KEY, checked)
	}

	fun enableLed(checked: Boolean)
	{
		StaticProvider.Prefs.setPrefBoolean(StaticProvider.Prefs.PrefType.LED_KEY, checked)
	}

	fun onClearMemory()
	{
		StaticProvider.Memory.resetMemory(context)
		Toast.makeText(context, R.string.toast_cache_cleared, Toast.LENGTH_SHORT).show()
	}

	fun onAbout()
	{
		val intent = Intent(context, AboutActivity::class.java)
		context.startActivity(intent)
	}


}