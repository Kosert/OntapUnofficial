package me.kosert.ontap.ui.activities.main.fragments.cities

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import kotlinx.android.synthetic.main.cities_fragment.*
import me.kosert.ontap.R
import me.kosert.ontap.model.Multitap
import me.kosert.ontap.ui.activities.main.adapters.CitySpinnerAdapter
import me.kosert.ontap.ui.activities.main.adapters.RecyclerMultitapAdapter
import me.kosert.ontap.ui.activities.main.fragments.MainAbstractFragment

/**
 * Created by Kosert on 2018-02-10.
 */

class CitiesFragment : MainAbstractFragment()
{
	override val recyclerView: RecyclerView
		get() = cities_recycler

	override val controller: CitiesController = CitiesController()

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
		return inflater.inflate(R.layout.cities_fragment, container, false)
	}

	override fun onViewCreated(view: View?, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)

		val spinnerAdapter = CitySpinnerAdapter(context, dataProvider.cities)
		cities_spinner.adapter = spinnerAdapter

		val recyclerAdapter = RecyclerMultitapAdapter(context, mutableListOf())
		cities_recycler.adapter = recyclerAdapter
		cities_recycler.layoutManager = LinearLayoutManager(context)

		cities_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
		{
			override fun onNothingSelected(parent: AdapterView<*>?) {}

			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
			{
				controller.onCitySelected(position)
			}
		}

		cities_swipe.setOnRefreshListener {
			controller.onRefresh()
		}

		val callbacks = object : ICitiesCallbacks
		{
			override fun setOnMultitapClick(onClickCallback: RecyclerMultitapAdapter.ItemClickCallback)
			{
				recyclerAdapter.setOnClickCallback(onClickCallback)
			}

			override fun recyclerNotifyPosition(position: Int)
			{
				Handler().post {
					recyclerAdapter.notifyItemChanged(position)
				}
			}

			override fun getLastToLoadPosition(): Int =
					this@CitiesFragment.getLastToLoadPosition()

			override fun getSpinnerPosition(): Int
			{
				return cities_spinner.selectedItemPosition
			}

			override var isRefreshing: Boolean
				get() = cities_swipe.isRefreshing
				set(value)
				{
					cities_swipe.isRefreshing = value
				}

			override fun spinnerNotify()
			{
				spinnerAdapter.notifyDataSetChanged()
			}

			override fun recyclerClear()
			{
				recyclerAdapter.list.clear()
				recyclerAdapter.notifyDataSetChanged()
			}

			override fun recyclerSetContent(list: List<Multitap>, forceRefresh: Boolean)
			{
				recyclerAdapter.list.clear()
				recyclerAdapter.list.addAll(list)
				recyclerAdapter.notifyDataSetChanged()
				controller.updateDetailsVisible()
				if (forceRefresh)
					controller.requestDetailsRefresh()
			}
		}

		controller.onCreate(context, callbacks)
	}
}