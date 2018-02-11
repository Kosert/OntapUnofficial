package me.kosert.ontap.data

import me.kosert.ontap.data.callbacks.NetworkCallback
import me.kosert.ontap.model.City

/**
 * Created by Kosert on 2018-02-10.
 */
interface IDataProvider
{
	var cities : MutableList<City>

	/**
	 *  Fetches the city list.
	 *	Try local list, if fail -> download from web, if fail -> loads from memory
	 *  Calls [NetworkCallback.onFailure] when network fetch fails
	 *  Calls [NetworkCallback.onSuccess] when data is loaded from either source
	 */
	fun loadCityList(callback : NetworkCallback)

	/**
	 *  Fetches the city list.
	 *  Try local list, if fail -> download from web, if fail -> loads from memory
	 *  If [forceRefresh] is true -> skip checking local list
	 *  Calls [NetworkCallback.onFailure] when network fetch fails
	 *  Calls [NetworkCallback.onSuccess] when data is loaded from either source
	 */
	fun loadCityList(callback : NetworkCallback, forceRefresh : Boolean)

	/**
	 *  Fetches multitap list of [city].
	 *  Try loading from memory, if fail -> download from web
	 *  Calls [NetworkCallback.onFailure] when network fetch fails
	 *  Calls [NetworkCallback.onSuccess] when data is loaded from either source
	 */
	fun loadMultitapList(city: City, callback : NetworkCallback)

	/**
	 *  Fetches multitap list of [city].
	 *  Try loading from memory, if fail -> download from web
	 *  If [forceRefresh] is true -> download from web, if fail -> load from memory
	 *  Calls [NetworkCallback.onFailure] when network fetch fails
	 *  Calls [NetworkCallback.onSuccess] when data is loaded from either source
	 */
	fun loadMultitapList(city: City, callback : NetworkCallback, forceRefresh: Boolean)

}