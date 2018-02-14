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

	private lateinit var multitapDetails: MultitapDetails

	fun onCreate(multitapActivity: MultitapActivity, callbacks: IMultitapCallbacks)
	{
		context = multitapActivity
		this.callbacks = callbacks
	}

	fun parseIntent(multitapJson : String, detailsJson: String?)
	{
		val multitap = StaticProvider.getGson().fromJson(multitapJson, Multitap::class.java)
		detailsJson?.let {
			val details = StaticProvider.getGson().fromJson(it, MultitapDetails::class.java)
			callbacks.setAddress(details.address)
			multitapDetails = details
		}
		callbacks.setTitle(multitap.name)

		getBeerList(multitap)
	}

	private fun getBeerList(multitap : Multitap)
	{
		dataProvider.loadMultitapWithBeerList(multitap, object : NetworkCallback
		{
			override fun onSuccess()
			{
				logger.i("BeerList loaded")
				multitap.details?.let {
					callbacks.setAddress(it.address)
					multitapDetails = it
				}

				callbacks.setBeerList(multitap.beers)
			}

			override fun onFailure()
			{
				Toast.makeText(context, context.getString(R.string.netwok_error), Toast.LENGTH_SHORT).show()
			}
		})

	}

	fun onStarClicked()
	{
		Toast.makeText(context, context.getString(R.string.toast_not_implemented), Toast.LENGTH_SHORT).show()
	}

	fun onMapClicked()
	{
		Toast.makeText(context, context.getString(R.string.toast_not_implemented), Toast.LENGTH_SHORT).show()
	}

	fun onInfoClicked()
	{
		Toast.makeText(context, context.getString(R.string.toast_not_implemented), Toast.LENGTH_SHORT).show()
	}
}