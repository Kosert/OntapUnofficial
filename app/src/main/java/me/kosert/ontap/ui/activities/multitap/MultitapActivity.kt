package me.kosert.ontap.ui.activities.multitap

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.multitap_activity.*
import me.kosert.ontap.R
import me.kosert.ontap.model.BeerItem
import me.kosert.ontap.ui.activities.AbstractActivity
import me.kosert.ontap.ui.activities.multitap.adapters.RecyclerBeerAdapter

/**
 * Created by Kosert on 2018-02-14.
 */
class MultitapActivity : AbstractActivity()
{
	companion object
	{
		const val EXTRA_MULTITAP = "EXTRA_MULTITAP"
		const val EXTRA_DETAILS = "EXTRA_DETAILS"
	}

	private val multitapController = MultitapController()
	private val beerList = mutableListOf<BeerItem>()

	private var menuStar : MenuItem? = null
	private var menuNotification : MenuItem? = null

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.multitap_activity)

		setSupportActionBar(multitap_toolbar)
		supportActionBar!!.setDisplayHomeAsUpEnabled(true)

		val recyclerAdapter = RecyclerBeerAdapter(this@MultitapActivity, beerList)
		multitap_recycler.adapter = recyclerAdapter
		multitap_recycler.layoutManager = LinearLayoutManager(this@MultitapActivity)

		val callbacks = object : IMultitapCallbacks
		{
			override fun setTitle(name: String)
			{
				supportActionBar?.title = name
			}

			override fun setAddress(address: String)
			{
				multitap_address.text = address
			}

			override fun setStarred()
			{
				menuStar?.title = getString(R.string.menu_unstar)
				menuStar?.icon = getDrawable(R.drawable.ic_star_24dp)
			}

			override fun setUnstarred()
			{
				menuStar?.title = getString(R.string.menu_star)
				menuStar?.icon = getDrawable(R.drawable.ic_star_border_24dp)
			}

			override fun clearBeerList()
			{
				beerList.clear()
				recyclerAdapter.notifyDataSetChanged()
			}

			override fun setBeerList(list: List<BeerItem>)
			{
				beerList.clear()
				beerList.addAll(list)
				recyclerAdapter.notifyDataSetChanged()
			}

			override var isRefreshing: Boolean
				get() = multitap_swipe_beers.isRefreshing
				set(value)
				{
					multitap_swipe_beers.isRefreshing = value
				}
		}

		multitap_swipe_beers.setOnRefreshListener {
			multitapController.onRefresh()
		}

		multitapController.onCreate(this@MultitapActivity, callbacks)

		val multitapJson = intent.getStringExtra(EXTRA_MULTITAP)
		val detailsJson = intent.getStringExtra(EXTRA_DETAILS)

		multitapController.parseIntent(multitapJson, detailsJson)
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean
	{
		menuInflater.inflate(R.menu.multitap_menu, menu)
		menuStar = menu?.findItem(R.id.multitap_menu_star)
		menuNotification = menu?.findItem(R.id.multitap_menu_notification)
		multitapController.onCreateMenu()
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean
	{
		return when (item?.itemId)
		{
			android.R.id.home ->
			{
				onBackPressed()
				true
			}
			R.id.multitap_menu_star ->
			{
				multitapController.onStarClicked()
				true
			}
			R.id.multitap_menu_notification ->
			{
				multitapController.onNotificationClicked()
				true
			}
			R.id.multitap_menu_info ->
			{
				multitapController.onInfoClicked()
				true
			}
			else ->
			{
				super.onOptionsItemSelected(item)
			}
		}
	}
}