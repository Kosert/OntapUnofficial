package me.kosert.ontap.ui.activities.multitap

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.multitap_activity.*
import me.kosert.ontap.R
import me.kosert.ontap.ui.activities.AbstractActivity

/**
 * Created by Kosert on 2018-02-14.
 */
class MultitapActivity : AbstractActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.multitap_activity)

		setSupportActionBar(multitap_toolbar)
		supportActionBar!!.setDisplayHomeAsUpEnabled(true)


	}


	override fun onCreateOptionsMenu(menu: Menu?): Boolean
	{
		menuInflater.inflate(R.menu.multitap_menu, menu)
		val menuStar = menu?.findItem(R.id.multitap_menu_star)

		//TODO check if in Favorites
		//	menuStar?.icon = getDrawable(R.drawable.ic_star_24dp)
		//	menuStar?.title = getString(R.string.menu_unstar)

		return true
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean
	{
		return when(item?.itemId)
		{
			R.id.home -> {
				onBackPressed()
				true
			}
			else -> {
				super.onOptionsItemSelected(item)
			}
		}
	}
}