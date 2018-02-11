package me.kosert.ontap.data.callbacks

/**
 * Created by Kosert on 2018-02-10.
 */

interface NetworkCallback
{
	/**
	 * DataProvider loaded the requested data
	 */
	fun onSuccess()

	/**
	 * DataProvider failed to load data from web.
	 * Some data may have been loaded from memory, but this data can be obsolete.
	 */
	fun onFailure()
}