package me.kosert.ontap.ui.activities.main.adapters

/**
 * Created by Kosert on 2018-02-16.
 */
interface ItemTouchHelperCallbacks
{
	fun onItemMove(fromPosition: Int, toPosition: Int) : Boolean
	fun onItemRemove(position: Int)
}