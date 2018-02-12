package me.kosert.ontap.ui.activities.main.fragments

/**
 * Created by Kosert on 2018-02-12.
 */
interface ICallbacks
{
	fun getLastToLoadPosition() : Int
	fun recyclerNotifyPosition(position: Int)
}