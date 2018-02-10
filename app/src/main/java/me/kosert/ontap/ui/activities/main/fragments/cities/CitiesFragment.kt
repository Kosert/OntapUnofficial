package me.kosert.ontap.ui.activities.main.fragments.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.cities_fragment.*
import me.kosert.ontap.R
import me.kosert.ontap.data.DataProvider
import me.kosert.ontap.ui.activities.main.CitySpinnerAdapter
import me.kosert.ontap.ui.activities.main.fragments.MainAbstractFragment

/**
 * Created by Kosert on 2018-02-10.
 */

class CitiesFragment : MainAbstractFragment()
{
	private val citiesController = CitiesController()

	companion object
	{
		fun newInstance() : CitiesFragment
		{
			val fragment = CitiesFragment()
			val args = Bundle()

			fragment.arguments = args
			return fragment
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
	{
		val view = inflater.inflate(R.layout.cities_fragment, container, false)

		return view
	}

	override fun onViewCreated(view: View?, savedInstanceState: Bundle?)
	{
		val spinnerAdapter = CitySpinnerAdapter(context, DataProvider.cities)
		cities_spinner.adapter = spinnerAdapter

		citiesController.onCreate(context, spinnerAdapter)
	}

}