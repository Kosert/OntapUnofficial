package me.kosert.ontap.ui.activities.main.fragments.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.kosert.ontap.R
import me.kosert.ontap.ui.activities.main.fragments.MainAbstractFragment

/**
 * Created by Kosert on 2018-02-10.
 */

class CitiesFragment : MainAbstractFragment()
{
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

}