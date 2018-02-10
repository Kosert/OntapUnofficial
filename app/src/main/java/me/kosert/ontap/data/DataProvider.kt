package me.kosert.ontap.data

import me.kosert.ontap.data.callbacks.CityListCallback
import me.kosert.ontap.data.callbacks.NetworkCallback
import me.kosert.ontap.model.City

/**
 * Created by Kosert on 2018-02-10.
 */
object DataProvider : IDataProvider
{
	var cities = mutableListOf<City>()

	override fun loadCityList(callback: CityListCallback)
	{
		loadCityList(callback, false)
	}

	override fun loadCityList(callback : CityListCallback, forceRefresh: Boolean)
	{
		if (!forceRefresh && cities.isNotEmpty())
			callback.onSuccess(cities)
		else
		{
			DataRetriever.downloadCityList(object : NetworkCallback{
				override fun onSuccess()
				{
					StaticProvider.Memory.saveCityList(cities)
					callback.onSuccess(cities)
				}

				override fun onFailure()
				{
					callback.onFailure()
					StaticProvider.Memory.getCityList()?.let {
						cities.clear()
						cities.addAll(it)
						callback.onSuccess(cities)
					}
				}
			})
		}
	}
}