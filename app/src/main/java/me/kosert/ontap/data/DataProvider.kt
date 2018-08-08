package me.kosert.ontap.data

import android.os.Handler
import me.kosert.ontap.data.callbacks.NetworkCallback
import me.kosert.ontap.model.BeerItem
import me.kosert.ontap.model.City
import me.kosert.ontap.model.Multitap
import me.kosert.ontap.util.Logger

/**
 * Singleton implementation of [IDataProvider].
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

	override fun loadMultitapDetails(multitap: Multitap, callback: NetworkCallback)
	{
		loadMultitapDetails(multitap, callback, false)
	}

	override fun loadMultitapDetails(multitap: Multitap, callback: NetworkCallback, forceRefresh: Boolean)
	{
		multitap.detailsLoading = true

		val refresh = (multitap.details?.invalidated ?: false) || forceRefresh

		if (!refresh)
		{
			if (loadMultitapDetailsFromMemory(multitap))
			{
				callback.onSuccess()
				multitap.detailsLoading = false
				return
			}
		}

		WebRetriever.downloadMultitapDetails(multitap, object : NetworkCallback
		{
			override fun onSuccess()
			{
				StaticProvider.Memory.saveMultitapDetails(multitap)
				StaticProvider.Favorites.updateFavorite(multitap)
				multitap.detailsLoading = false
				callback.onSuccess()
			}

			override fun onFailure()
			{
				if (refresh)
				{
					if (loadMultitapDetailsFromMemory(multitap))
					{
						multitap.detailsLoading = false
						callback.onSuccess()
					}
				}
				multitap.detailsLoading = false
				callback.onFailure()
			}
		}, false)

	}

	private val beerLists = mutableMapOf<String, List<BeerItem>>()

	fun clearMap()
	{
		beerLists.clear()
	}

	private fun addToMap(pubUrl: String, list: List<BeerItem>)
	{
		beerLists[pubUrl] = list
		Handler().postDelayed({
			beerLists.remove(pubUrl)
		}, 300000)
	}

	override fun loadMultitapWithBeerList(multitap: Multitap, callback: NetworkCallback, refresh: Boolean)
	{
		if (!refresh)
		{
			beerLists[multitap.url]?.let {
				multitap.beers.clear()
				multitap.beers.addAll(it)
				callback.onSuccess()
				return
			}
		}

		multitap.detailsLoading = true
		WebRetriever.downloadMultitapDetails(multitap, object : NetworkCallback
		{
			override fun onSuccess()
			{
				StaticProvider.Memory.saveMultitapDetails(multitap)
				StaticProvider.Favorites.updateFavorite(multitap)
				addToMap(multitap.url, multitap.beers)
				multitap.detailsLoading = false
				callback.onSuccess()
			}

			override fun onFailure()
			{
				if (loadMultitapDetailsFromMemory(multitap))
				{
					multitap.detailsLoading = false
					callback.onSuccess()
				}
				multitap.detailsLoading = false
				callback.onFailure()
			}
		}, true)
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

	private fun loadMultitapDetailsFromMemory(multitap: Multitap) : Boolean
	{
		StaticProvider.Memory.getMultitapDetails(multitap)?.let {
			multitap.details = it
			return true
		}
		return false
	}
}