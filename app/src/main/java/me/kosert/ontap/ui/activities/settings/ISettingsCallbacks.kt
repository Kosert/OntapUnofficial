package me.kosert.ontap.ui.activities.settings

import me.kosert.ontap.ui.activities.settings.adapters.RecyclerNotificationAdapter

/**
 * Created by Kosert on 2018-02-16.
 */
interface ISettingsCallbacks
{
	fun setPrefs(notifis: Boolean, sound: Boolean, vibrate: Boolean, led: Boolean, syncPosition: Int)
	fun setRecyclerCallback(onClickCallback: RecyclerNotificationAdapter.ItemClickCallback)
	fun notifyRecycler()
}