package me.kosert.ontap.ui.activities.main.fragments.cities

import me.kosert.ontap.model.Multitap

/**
 * Created by Kosert on 2018-02-11.
 */
interface ICitiesCallbacks
{
	fun spinnerNotify()
	fun recyclerClear()
	fun recyclerSetContent(list: List<Multitap>)
	fun getSpinnerPosition() : Int
	var isRefreshing : Boolean
}