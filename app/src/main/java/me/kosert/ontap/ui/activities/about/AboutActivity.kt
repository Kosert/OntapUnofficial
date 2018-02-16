package me.kosert.ontap.ui.activities.about

import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.about_activity.*
import me.kosert.ontap.BuildConfig
import me.kosert.ontap.R
import me.kosert.ontap.ui.activities.AbstractActivity

/**
 * Created by Kosert on 2018-02-16.
 */

class AboutActivity : AbstractActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.about_activity)

		setSupportActionBar(about_toolbar)
		supportActionBar?.title = getString(R.string.about_title)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		about_version.text = getString(R.string.version, BuildConfig.VERSION_NAME)
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean
	{
		if (item?.itemId == android.R.id.home)
		{
			onBackPressed()
			return true
		}
		return super.onOptionsItemSelected(item)
	}
}