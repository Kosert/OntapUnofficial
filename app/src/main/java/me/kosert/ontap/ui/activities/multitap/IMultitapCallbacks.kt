package me.kosert.ontap.ui.activities.multitap

import me.kosert.ontap.model.BeerItem
import me.kosert.ontap.model.Multitap

/**
 * Created by Kosert on 2018-02-14.
 */
interface IMultitapCallbacks
{
	fun setTitle(name : String)
	fun setAddress(address : String)
	fun setStarred()
	fun setUnstarred()
	fun clearBeerList()
	fun setBeerList(list : List<BeerItem>)
	fun showDialog(multitap: Multitap)
	var isRefreshing : Boolean
}