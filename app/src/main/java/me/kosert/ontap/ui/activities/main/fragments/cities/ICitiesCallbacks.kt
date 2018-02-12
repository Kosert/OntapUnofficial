package me.kosert.ontap.ui.activities.main.fragments.cities

import me.kosert.ontap.model.Multitap
import me.kosert.ontap.ui.activities.main.fragments.ICallbacks

/**
 * Created by Kosert on 2018-02-11.
 */
interface ICitiesCallbacks : ICallbacks
{
	fun spinnerNotify()
	fun recyclerClear()
	fun recyclerSetContent(list: List<Multitap>, forceRefresh: Boolean)
	fun getSpinnerPosition() : Int
	var isRefreshing : Boolean
}