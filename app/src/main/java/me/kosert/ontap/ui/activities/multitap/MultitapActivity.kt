package me.kosert.ontap.ui.activities.multitap

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.multitap_activity.*
import me.kosert.ontap.R
import me.kosert.ontap.model.BeerItem
import me.kosert.ontap.ui.activities.AbstractActivity
import me.kosert.ontap.ui.activities.main.adapters.RecyclerBeerAdapter

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

			override fun setAddress(address : String)
			{
				multitap_address.text = address
			}

			override fun setBeerList(list: List<BeerItem>)
			{
				beerList.clear()
				beerList.addAll(list)
				recyclerAdapter.notifyDataSetChanged()
			}
		}

		multitapController.onCreate(this@MultitapActivity, callbacks)

		val multitapJson = intent.getStringExtra(EXTRA_MULTITAP)
		val detailsJson = intent.getStringExtra(EXTRA_DETAILS)

		multitapController.parseIntent(multitapJson, detailsJson)
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean
	{
		menuInflater.inflate(R.menu.multitap_menu, menu)
		val menuStar = menu?.findItem(R.id.multitap_menu_star)

		//TODO check if in Favorites (through controller)
		//	menuStar?.icon = getDrawable(R.drawable.ic_star_24dp)
		//	menuStar?.title = getString(R.string.menu_unstar)

		return true
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean
	{
		return when(item?.itemId)
		{
			android.R.id.home -> {
				onBackPressed()
				true
			}
			R.id.multitap_menu_star -> {
				multitapController.onStarClicked()
				true
			}
			R.id.multitap_menu_map -> {
				multitapController.onMapClicked()
				true
			}
			R.id.multitap_menu_info -> {
				multitapController.onInfoClicked()
				true
			}
			else -> {
				super.onOptionsItemSelected(item)
			}
		}
	}
}