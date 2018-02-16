package me.kosert.ontap.ui.activities.main

import android.view.MenuItem

/**
 * Created by Kosert on 2018-02-15.
 */
interface IMainCallbacks
{
	fun enableBackButton(enable : Boolean)
	fun getMenuFirstItem() : MenuItem?
	fun getMenuSecondItem() : MenuItem?
	var currentPage : Int
	var isEditMode : Boolean
}