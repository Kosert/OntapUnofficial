package me.kosert.ontap.data.callbacks

/**
 * Created by Kosert on 2018-02-10.
 */

interface NetworkCallback
{
	/**
	 * Called when requested data has been loaded
	 */
	fun onSuccess()

	/**
	 * Called when there was an error when loading requested data
	 * Some data may have been loaded from memory, but this data can be obsolete.
	 */
	fun onFailure()
}