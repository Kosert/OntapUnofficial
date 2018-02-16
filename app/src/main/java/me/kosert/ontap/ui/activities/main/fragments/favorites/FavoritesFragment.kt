package me.kosert.ontap.ui.activities.main.fragments.favorites

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.favorites_fragment.*
import me.kosert.ontap.R
import me.kosert.ontap.data.StaticProvider
import me.kosert.ontap.ui.activities.main.adapters.RecyclerItemTouchHelper
import me.kosert.ontap.ui.activities.main.adapters.RecyclerMultitapAdapter
import me.kosert.ontap.ui.activities.main.fragments.ICallbacks
import me.kosert.ontap.ui.activities.main.fragments.MainAbstractFragment

/**
 * Created by Kosert on 2018-02-10.
 */

class FavoritesFragment : MainAbstractFragment()
{
	override val recyclerView: RecyclerView
		get() = favs_recycler

	override val controller = FavoritesController()

	lateinit var recyclerAdapter : RecyclerMultitapAdapter
	lateinit var recyclerTouchOptions : RecyclerItemTouchHelper

	companion object
	{
		fun newInstance() : FavoritesFragment
		{
			val fragment = FavoritesFragment()
			val args = Bundle()

			fragment.arguments = args
			return fragment
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
	{
		return inflater.inflate(R.layout.favorites_fragment, container, false)
	}

	override fun onViewCreated(view: View?, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)

		recyclerAdapter = RecyclerMultitapAdapter(context, StaticProvider.Favorites.favoritesList)
		favs_recycler.adapter = recyclerAdapter
		favs_recycler.layoutManager = LinearLayoutManager(context)

		recyclerTouchOptions = RecyclerItemTouchHelper(recyclerAdapter, false)
		val touchHelper = ItemTouchHelper(recyclerTouchOptions)
		touchHelper.attachToRecyclerView(favs_recycler)

		val callbacks = object : ICallbacks
		{

			override fun getLastToLoadPosition(): Int
			{
				return this@FavoritesFragment.getLastToLoadPosition()
			}

			override fun recyclerNotifyPosition(position: Int)
			{
				Handler().post {
					recyclerAdapter.notifyItemChanged(position)
				}
			}

			override fun setOnMultitapClick(onClickCallback: RecyclerMultitapAdapter.ItemClickCallback)
			{
				recyclerAdapter.setOnClickCallback(onClickCallback)
			}
		}

		controller.onCreate(context, callbacks)
	}

	override fun onStart()
	{
		super.onStart()
		notifyFavoritesChanged()
	}

	fun notifyFavoritesChanged()
	{
		recyclerAdapter.notifyDataSetChanged()
		if (recyclerAdapter.itemCount != 0)
			favs_text_empty.visibility = View.GONE
		else
			favs_text_empty.visibility = View.VISIBLE
	}
}