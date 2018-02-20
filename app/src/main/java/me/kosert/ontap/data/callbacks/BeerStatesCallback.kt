package me.kosert.ontap.data.callbacks

import me.kosert.ontap.model.BeerState

/**
 * Created by Kosert on 2018-02-18.
 */

interface BeerStatesCallback
{
	fun onSuccess(list: List<BeerState>)
	fun onFailure()
}