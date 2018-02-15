package me.kosert.ontap.ui.activities.settings

import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.settings_activity.*
import me.kosert.ontap.R
import me.kosert.ontap.ui.activities.AbstractActivity

/**
 * Created by Kosert on 2018-02-15.
 */

class SettingsActivity : AbstractActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.settings_activity)

		setSupportActionBar(settings_toolbar)
		supportActionBar?.title = "Settings"
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean
	{
		if (item?.itemId == android.R.id.home) {
			onBackPressed()
			return true
		}
		return super.onOptionsItemSelected(item)
	}
}