package me.kosert.ontap.data

import me.kosert.ontap.data.callbacks.NetworkCallback
import me.kosert.ontap.model.City
import me.kosert.ontap.model.Multitap

/**
 * Main source of all data.
 * Created by Kosert on 2018-02-10.
 */
interface IDataProvider
{
	/**
	 * The main city list
	 */
	var cities : MutableList<City>

	/**
	 *  Fetches the city list.
	 *  Tries local list first. Downloads data from web if it is empty
	 *  or loads from memory cache as fallback if web fails.
	 *  @param callback
	 *  Calls [NetworkCallback.onFailure] when network fetch fails
	 *  and [NetworkCallback.onSuccess] when data is loaded from either source.
	 */
	fun loadCityList(callback : NetworkCallback)

	/**
	 *  Fetches the city list.
	 *  Tries local list first. Downloads data from web if it is empty
	 *  or loads from memory cache if fetching from web fails.
	 *  @param callback
	 *  Calls [NetworkCallback.onFailure] when network fetch fails
	 *  and [NetworkCallback.onSuccess] when data is loaded from either source.
	 *  @param forceRefresh
	 *  If true skips checking local list
	 */
	fun loadCityList(callback : NetworkCallback, forceRefresh : Boolean)

	/**
	 *  Fetches multitap list of city.
	 *  Tries loading from memory cache first and downloads from web if
	 *  that fails.
	 *  @param city
	 *  [City] which [City.multitaps] will be populated
	 *  @param callback
	 *  Calls [NetworkCallback.onFailure] when network fetch fails
	 *  and [NetworkCallback.onSuccess] when data is loaded from either source
	 */
	fun loadMultitapList(city: City, callback : NetworkCallback)

	/**
	 *  Fetches multitap list of city.
	 *  Tries loading from memory cache first and downloads from web if
	 *  that fails.
	 *  @param city
	 *  [City] which [City.multitaps] will be populated
	 *  @param callback
	 *  Calls [NetworkCallback.onFailure] when network fetch fails
	 *  and [NetworkCallback.onSuccess] when data is loaded from either source
	 *  @param forceRefresh
	 *  If true tries download from web first, then load from memory cache if that fails.
	 */
	fun loadMultitapList(city: City, callback : NetworkCallback, forceRefresh: Boolean)

	/**
	 *  Fetches detailed information about multitap.
	 * 	Tries loading from memory cache first and downloads from web if
	 *  that fails.
	 *  @param multitap
	 *  [Multitap] which [Multitap.details] object will be updated
	 *  @param callback
	 *  Calls [NetworkCallback.onFailure] when network fetch fails
	 *  and [NetworkCallback.onSuccess] when data is loaded from either source
	 */
	fun loadMultitapDetails(multitap: Multitap, callback : NetworkCallback)

	/**
	 *  Fetches detailed information about multitap.
	 * 	Tries loading from memory cache first and downloads from web if
	 *  that fails.
	 *  @param multitap
	 *  [Multitap] which [Multitap.details] object will be updated
	 *  @param callback
	 *  Calls [NetworkCallback.onFailure] when network fetch fails
	 *  and [NetworkCallback.onSuccess] when data is loaded from either source
	 *  @param forceRefresh
	 *  If true tries download from web first, then load from memory cache if that fails.
	 */
	fun loadMultitapDetails(multitap: Multitap, callback : NetworkCallback, forceRefresh: Boolean)

	/**
	 *  Fetches detailed information about multitap.
	 *  In addition to [loadMultitapDetails] this method also
	 *  parses all beers and adds them to [Multitap.beers]
	 * 	Tries loading from memory cache first and downloads from web if
	 *  that fails.
	 *  @param multitap
	 *  [Multitap] which [Multitap.details] object will be updated
	 *  @param callback
	 *  Calls [NetworkCallback.onFailure] when network fetch fails
	 *  and [NetworkCallback.onSuccess] when data is loaded from either source
	 *  @param refresh
	 *  If true tries download from web first, then load from memory cache if that fails.
	 */
	fun loadMultitapWithBeerList(multitap: Multitap, callback : NetworkCallback, refresh: Boolean)
}