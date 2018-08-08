package me.kosert.ontap.data.callbacks

import me.kosert.ontap.model.BeerState

/**
 * Created by Kosert on 2018-02-18.
 */

interface BeerStatesCallback
{
	/**
	 * Called when requested data has been downloaded
	 * @param list
	 * List containing downloaded [BeerState] objects
	 */
	fun onSuccess(list: List<BeerState>)

	/**
	 * Called when there was an error when loading requested data
	 */
	fun onFailure()
}