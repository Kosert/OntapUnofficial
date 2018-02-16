package me.kosert.ontap.ui.activities.main.adapters

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

/**
 * Created by Kosert on 2018-02-16.
 */
class RecyclerItemTouchHelper(private val callbacks: ItemTouchHelperCallbacks, var editEnabled : Boolean) : ItemTouchHelper.Callback()
{
	override fun isLongPressDragEnabled(): Boolean
	{
		return editEnabled
	}

	override fun isItemViewSwipeEnabled(): Boolean
	{
		return editEnabled
	}

	override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
	{
		callbacks.onItemRemove(viewHolder.adapterPosition)
	}

	override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int
	{
		val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
		val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
		return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
	}

	override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean
	{
		callbacks.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
		return true
	}

}