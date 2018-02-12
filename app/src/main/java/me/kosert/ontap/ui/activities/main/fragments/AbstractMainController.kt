package me.kosert.ontap.ui.activities.main.fragments

import android.content.Context
import android.support.annotation.CallSuper
import me.kosert.ontap.data.DataProvider
import me.kosert.ontap.data.IDataProvider
import me.kosert.ontap.data.callbacks.NetworkCallback
import me.kosert.ontap.model.Multitap
import me.kosert.ontap.util.Logger

/**
 * Created by Kosert on 2018-02-10.
 */
abstract class AbstractMainController
{
	protected val dataProvider = DataProvider as IDataProvider
	protected lateinit var context : Context

	protected abstract val callbacks : ICallbacks
	protected abstract val logger : Logger

	protected abstract fun getDisplayedList() : List<Multitap>

	@CallSuper
	open fun onCreate(context: Context, callbacks: ICallbacks)
	{
		this.context = context
	}

	fun onScroll()
	{
		updateDetailsVisible()
	}

	fun requestDetailsRefresh()
	{
		getDisplayedList().forEach {
			it.details?.let {
				it.invalidated = true
			}
		}
	}

	/**
	 *
	 */
	fun updateDetailsVisible()
	{
		val list = getDisplayedList()
		val shouldBeLoaded = list.take(callbacks.getLastToLoadPosition() + 1)

		shouldBeLoaded.forEachIndexed { index, multitap ->
			if ((multitap.details?.invalidated != false) && !multitap.detailsLoading)
			{
				dataProvider.loadMultitapDetails(multitap, object : NetworkCallback{
					override fun onSuccess()
					{
						callbacks.recyclerNotifyPosition(index)
					}

					override fun onFailure() {}
				})
			}
		}
	}
}