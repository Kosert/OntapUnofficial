package me.kosert.ontap.data

import me.kosert.ontap.data.callbacks.CityListCallback

/**
 * Created by Kosert on 2018-02-10.
 */
interface IDataProvider
{
	/**
	 *  Fetches the city list and passes it to [CityListCallback#onSuccess(list : List<City>)],
	 *  If list is not loaded -> download from web,
	 *  If fail -> loads from memory
	 */
	fun loadCityList(callback : CityListCallback)

	/**
	 *  Fetches the city list and passes it to [CityListCallback#onSuccess(list : List<City>)],
	 *  Clears already downloaded data and forces download,
	 *  If fail -> loads from memory
	 */
	fun loadCityList(callback : CityListCallback, forceRefresh : Boolean)

}