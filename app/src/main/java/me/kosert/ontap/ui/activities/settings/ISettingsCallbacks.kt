package me.kosert.ontap.ui.activities.settings

/**
 * Created by Kosert on 2018-02-16.
 */
interface ISettingsCallbacks
{
	fun setPrefs(notifis: Boolean, sound: Boolean, vibrate: Boolean, led: Boolean)
}