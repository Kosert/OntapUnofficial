package me.kosert.ontap.data.callbacks

import me.kosert.ontap.model.City

/**
 * Created by Kosert on 2018-02-10.
 */
interface CityListCallback
{
	fun onSuccess(list : List<City>)

	/**
	 * DataProvider failed to load data from web.
	 * Some data may have been loaded from memory, but this data can be obsolete.
	 */
	fun onFailure()
}