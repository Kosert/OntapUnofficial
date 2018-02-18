package me.kosert.ontap.ui.activities.settings

import android.content.Context
import android.content.Intent
import android.widget.Toast
import me.kosert.ontap.R
import me.kosert.ontap.data.StaticProvider
import me.kosert.ontap.ui.activities.about.AboutActivity

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

		//callbacks.setPrefs()
	}

	fun enableNotifications(checked: Boolean)
	{
		Toast.makeText(context, R.string.toast_not_implemented, Toast.LENGTH_SHORT).show()
	}

	fun enableSound(checked: Boolean)
	{
		Toast.makeText(context, R.string.toast_not_implemented, Toast.LENGTH_SHORT).show()
	}

	fun enableVibrate(checked: Boolean)
	{
		Toast.makeText(context, R.string.toast_not_implemented, Toast.LENGTH_SHORT).show()
	}

	fun enableLed(checked: Boolean)
	{
		Toast.makeText(context, R.string.toast_not_implemented, Toast.LENGTH_SHORT).show()
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