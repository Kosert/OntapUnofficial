package me.kosert.ontap.ui.activities.main.fragments.cities

import android.content.Context
import android.widget.Toast
import me.kosert.ontap.R
import me.kosert.ontap.data.callbacks.NetworkCallback
import me.kosert.ontap.model.City
import me.kosert.ontap.model.Multitap
import me.kosert.ontap.ui.activities.main.fragments.AbstractMainController
import me.kosert.ontap.ui.activities.main.fragments.ICallbacks
import me.kosert.ontap.util.Logger
import kotlin.properties.Delegates

/**
 * Created by Kosert on 2018-02-10.
 */
class CitiesController : AbstractMainController()
{
	override val logger = Logger("CitiesController")

	override var callbacks by Delegates.notNull<ICitiesCallbacks>()

	override fun getDisplayedList(): List<Multitap>
	{
		val position = callbacks.getSpinnerPosition()
		return dataProvider.cities[position].multitaps
	}

	override fun onCreate(context: Context, callbacks: ICallbacks)
	{
		super.onCreate(context, callbacks)
		this.callbacks = callbacks as ICitiesCallbacks

		if (dataProvider.cities.isEmpty())
			dataProvider.cities.add(0, City(this.context.getString(R.string.choose_city), "", 0))
		this.callbacks.spinnerNotify()

		dataProvider.loadCityList(object : NetworkCallback
		{
			override fun onSuccess()
			{
				dataProvider.cities.add(0, City(this@CitiesController.context.getString(R.string.choose_city), "", 0))
				this@CitiesController.callbacks.spinnerNotify()
				logger.i("City list loaded")
			}

			override fun onFailure()
			{
				Toast.makeText(this@CitiesController.context, R.string.netwok_error, Toast.LENGTH_SHORT).show()
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
			if (!refresh) callbacks.recyclerSetContent(selectedCity.multitaps, false)
			callbacks.isRefreshing = false
		}
		else
		{
			dataProvider.loadMultitapList(selectedCity, object : NetworkCallback
			{
				override fun onSuccess()
				{
					callbacks.recyclerSetContent(selectedCity.multitaps, refresh)
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