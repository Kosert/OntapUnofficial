package me.kosert.ontap.ui.activities.multitap

import android.content.Context
import android.widget.Toast
import me.kosert.ontap.R
import me.kosert.ontap.data.DataProvider
import me.kosert.ontap.data.IDataProvider
import me.kosert.ontap.data.StaticProvider
import me.kosert.ontap.data.callbacks.NetworkCallback
import me.kosert.ontap.model.Multitap
import me.kosert.ontap.model.MultitapDetails
import me.kosert.ontap.util.ExternalUtil
import me.kosert.ontap.util.Logger


/**
 * Created by Kosert on 2018-02-14.
 */
class MultitapController
{
	private val logger = Logger("MultitapController")
	private val dataProvider = DataProvider as IDataProvider

	private lateinit var context : Context
	private lateinit var callbacks: IMultitapCallbacks

	private lateinit var multitap: Multitap

	fun onCreate(multitapActivity: MultitapActivity, callbacks: IMultitapCallbacks)
	{
		context = multitapActivity
		this.callbacks = callbacks
	}

	fun parseIntent(multitapJson : String, detailsJson: String?)
	{
		multitap = StaticProvider.getGson().fromJson(multitapJson, Multitap::class.java)
		detailsJson?.let {
			val details = StaticProvider.getGson().fromJson(it, MultitapDetails::class.java)
			callbacks.setAddress(details.address)
			multitap.details = details
		}
		callbacks.setTitle(multitap.name)

		callbacks.isRefreshing = true
		getBeerList(multitap, false)
	}

	fun onRefresh()
	{
		callbacks.clearBeerList()
		getBeerList(multitap, true)
	}

	private fun getBeerList(multitap : Multitap, refresh: Boolean)
	{
		dataProvider.loadMultitapWithBeerList(multitap, object : NetworkCallback
		{
			override fun onSuccess()
			{
				logger.i("BeerList loaded")
				multitap.details?.let {
					callbacks.setAddress(it.address)
					multitap.details = it
				}

				callbacks.setBeerList(multitap.beers)
				callbacks.isRefreshing = false
			}

			override fun onFailure()
			{
				Toast.makeText(context, context.getString(R.string.netwok_error), Toast.LENGTH_SHORT).show()
				callbacks.isRefreshing = false
			}
		}, refresh)
	}

	fun onCreateMenu()
	{
		handleFavorite(false)
	}

	fun onStarClicked()
	{
		handleFavorite(true)
	}

	fun onNotificationClicked()
	{
		if (StaticProvider.NotificationMemory.isNotificationEnabled(multitap))
		{
			StaticProvider.NotificationMemory.removeNotification(multitap)
			Toast.makeText(context, context.getString(R.string.toast_unfollowed, multitap.name), Toast.LENGTH_SHORT).show()
		}
		else
		{
			StaticProvider.NotificationMemory.addNotification(multitap)
			Toast.makeText(context, context.getString(R.string.toast_followed, multitap.name), Toast.LENGTH_SHORT).show()
		}
	}

	fun onMapClicked()
	{
		Toast.makeText(context, context.getString(R.string.toast_not_implemented), Toast.LENGTH_SHORT).show()
	}

	fun onInfoClicked()
	{
		callbacks.showDialog(multitap)
	}

	fun goToWebsite()
	{
		multitap.details?.let {
			if (it.website.isNotEmpty())
				ExternalUtil.launchWebsite(context, it.website)
		}
	}

	fun launchMessenger()
	{
		multitap.details?.let {
			if (it.messenger.isNotEmpty())
				ExternalUtil.launchMessenger(context, it.messenger)
		}
	}

	fun launchPhone()
	{
		multitap.details?.let {
			if (it.phone.isNotEmpty())
				ExternalUtil.launchDialer(context, it.phone)
		}
	}

	private fun handleFavorite(modify: Boolean)
	{
		if (StaticProvider.Favorites.isFavorite(multitap)) {
			if (modify)
			{
				callbacks.setUnstarred()
				Toast.makeText(context, context.getString(R.string.toast_unstarred, multitap.name), Toast.LENGTH_SHORT).show()
				StaticProvider.Favorites.removeFavorite(multitap)
			}
			else callbacks.setStarred()
		}
		else {
			if (modify)
			{
				callbacks.setStarred()
				Toast.makeText(context, context.getString(R.string.toast_starred, multitap.name), Toast.LENGTH_SHORT).show()
				StaticProvider.Favorites.addFavorite(multitap)
			}
			else callbacks.setUnstarred()
		}
	}
}