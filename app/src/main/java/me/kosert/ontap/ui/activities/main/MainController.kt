package me.kosert.ontap.ui.activities.main

import android.content.Context
import me.kosert.ontap.data.DataProvider
import me.kosert.ontap.data.IDataProvider


/**
 * Created by Kosert on 2018-02-10.
 */
class MainController
{
	private val dataProvider = DataProvider as IDataProvider
	private var context : Context? = null

	fun onCreate(cntxt: Context)
	{
		context = cntxt



	}
}