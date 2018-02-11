package me.kosert.ontap.ui.activities.main.fragments.cities

import android.content.Context
import android.widget.Toast
import me.kosert.ontap.R
import me.kosert.ontap.data.DataProvider
import me.kosert.ontap.data.IDataProvider
import me.kosert.ontap.data.callbacks.NetworkCallback
import me.kosert.ontap.model.City
import me.kosert.ontap.util.Logger

/**
 * Created by Kosert on 2018-02-10.
 */
class CitiesController
{
	private val logger = Logger("CitiesController")
	private val dataProvider = DataProvider as IDataProvider
	private lateinit var context : Context
	private lateinit var callbacks: ICitiesCallbacks

	fun onCreate(cntxt: Context, callback: ICitiesCallbacks)
	{
		context = cntxt
		callbacks = callback

		if (dataProvider.cities.isEmpty())
			dataProvider.cities.add(0, City(context!!.getString(R.string.choose_city), "", 0))
		callbacks.spinnerNotify()

		dataProvider.loadCityList(object : NetworkCallback
		{
			override fun onSuccess()
			{
				dataProvider.cities.add(0, City(context!!.getString(R.string.choose_city), "", 0))
				callbacks.spinnerNotify()
				logger.i("City list loaded")
			}

			override fun onFailure()
			{
				Toast.makeText(context, R.string.netwok_error, Toast.LENGTH_SHORT).show()
			}
		})
	}

	fun onCitySelected(position: Int)
	{
		loadMultitaps(dataProvider.cities[position], false)
	}

	fun onRefresh()
	{
		val position = callbacks.getSpinnerPosition()
		loadMultitaps(dataProvider.cities[position], true)
	}

	private fun loadMultitaps(selectedCity: City, refresh : Boolean)
	{
		if (!refresh) callbacks.isRefreshing = true
		callbacks.recyclerClear()

		if (selectedCity.url.isEmpty() || (selectedCity.multitaps.isNotEmpty() && !refresh))
		{
			if (!refresh) callbacks.recyclerSetContent(selectedCity.multitaps)
			callbacks.isRefreshing = false
		}
		else
		{
			dataProvider.loadMultitapList(selectedCity, object : NetworkCallback
			{
				override fun onSuccess()
				{
					callbacks.recyclerSetContent(selectedCity.multitaps)
					logger.i("Multitap list loaded")
					callbacks.isRefreshing = false
				}

				override fun onFailure()
				{
					Toast.makeText(context, R.string.netwok_error, Toast.LENGTH_SHORT).show()
					callbacks.isRefreshing = false
				}
			}, refresh)
		}


	}
}