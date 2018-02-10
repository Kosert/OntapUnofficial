package me.kosert.ontap.ui.activities.main.fragments.cities

import android.content.Context
import android.widget.Toast
import me.kosert.ontap.R
import me.kosert.ontap.data.DataProvider
import me.kosert.ontap.data.callbacks.CityListCallback
import me.kosert.ontap.model.City
import me.kosert.ontap.ui.activities.main.CitySpinnerAdapter
import me.kosert.ontap.util.Logger

/**
 * Created by Kosert on 2018-02-10.
 */
class CitiesController
{
	private val logger = Logger("CitiesController")
	private val dataProvider = DataProvider
	private var context : Context? = null
	private var spinnerAdapter : CitySpinnerAdapter? = null

	fun onCreate(cntxt: Context, adapter: CitySpinnerAdapter)
	{
		context = cntxt
		spinnerAdapter = adapter

		dataProvider.loadCityList(object : CityListCallback
		{
			override fun onSuccess(list: List<City>)
			{
				spinnerAdapter?.notifyDataSetChanged()
				logger.i("City list downloaded")
				logger.list(list)
			}

			override fun onFailure()
			{
				Toast.makeText(context, R.string.netwok_error, Toast.LENGTH_SHORT).show()
			}
		})

	}
}