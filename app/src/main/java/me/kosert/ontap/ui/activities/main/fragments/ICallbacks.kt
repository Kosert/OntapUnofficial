package me.kosert.ontap.ui.activities.main.fragments

import me.kosert.ontap.ui.activities.main.adapters.RecyclerMultitapAdapter

/**
 * Created by Kosert on 2018-02-12.
 */
interface ICallbacks
{
	fun getLastToLoadPosition() : Int
	fun recyclerNotifyPosition(position: Int)
	fun setOnMultitapClick(onClickCallback: RecyclerMultitapAdapter.ItemClickCallback)
}