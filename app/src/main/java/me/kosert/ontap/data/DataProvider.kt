package me.kosert.ontap.data

import me.kosert.ontap.data.callbacks.NetworkCallback
import me.kosert.ontap.model.City
import me.kosert.ontap.util.Logger

/**
 * Created by Kosert on 2018-02-10.
 */
object DataProvider : IDataProvider
{
	val logger = Logger("DataProvider")
	override var cities = mutableListOf<City>()

	override fun loadCityList(callback: NetworkCallback)
	{
		loadCityList(callback, false)
	}

	override fun loadCityList(callback : NetworkCallback, forceRefresh: Boolean)
	{
		if (!forceRefresh && cities.size > 1)
			callback.onSuccess()
		else
		{
			WebRetriever.downloadCityList(object : NetworkCallback{
				override fun onSuccess()
				{
					StaticProvider.Memory.saveCityList(cities)
					callback.onSuccess()
				}

				override fun onFailure()
				{
					callback.onFailure()
					StaticProvider.Memory.getCityList()?.let {
						cities.clear()
						cities.addAll(it)
						callback.onSuccess()
					}
				}
			})
		}
	}

	override fun loadMultitapList(city: City, callback: NetworkCallback)
	{
		loadMultitapList(city, callback, false)
	}

	override fun loadMultitapList(city: City, callback: NetworkCallback, forceRefresh: Boolean)
	{
		if (!forceRefresh)
		{
			if (loadMultitapListFromMemory(city))
			{
				callback.onSuccess()
				return
			}
		}

		WebRetriever.downloadCityMultitaps(city, object : NetworkCallback
		{
			override fun onSuccess()
			{
				StaticProvider.Memory.saveMultitapList(city)
				callback.onSuccess()
			}

			override fun onFailure()
			{
				if (forceRefresh)
				{
					if (loadMultitapListFromMemory(city))
						callback.onSuccess()
				}
				callback.onFailure()
			}
		})
	}

	private fun loadMultitapListFromMemory(city: City) : Boolean
	{
		StaticProvider.Memory.getMultitapList(city)?.let {
			city.multitaps.clear()
			city.multitaps.addAll(it)
			return true
		}
		return false
	}
}