package me.kosert.ontap.ui.activities.main.fragments

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import me.kosert.ontap.data.DataProvider
import me.kosert.ontap.data.IDataProvider

/**
 * Created by Kosert on 2018-02-10.
 */
abstract class MainAbstractFragment : Fragment()
{
	protected val dataProvider : IDataProvider = DataProvider

	protected abstract val recyclerView : RecyclerView

	protected abstract val controller : MainAbstractController

	@CallSuper
	override fun onViewCreated(view: View?, savedInstanceState: Bundle?)
	{
		recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener()
		{
			override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int)
			{
				controller.onScroll()
			}
		})
	}

	protected fun getLastToLoadPosition() : Int
	{
		val layoutManager : LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
		val max = layoutManager.itemCount - 1
		val lastVisible = layoutManager.findLastVisibleItemPosition()

		return when {
			(lastVisible == max) ->
				lastVisible
			(max - lastVisible < 3) ->
				lastVisible + (max - lastVisible)
			else ->
				lastVisible + 3
		}
	}
}