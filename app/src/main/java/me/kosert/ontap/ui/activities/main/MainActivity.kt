package me.kosert.ontap.ui.activities.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_activity.*
import me.kosert.ontap.R
import me.kosert.ontap.ui.activities.main.adapters.MainPagerAdapter

/**
 * Created by Kosert on 2018-02-10.
 */
class MainActivity : AppCompatActivity()
{
	private val mainController = MainController()

	override fun onCreate(savedInstanceState: Bundle?)
	{
		setTheme(R.style.AppTheme_NoActionBar)

		super.onCreate(savedInstanceState)
		setContentView(R.layout.main_activity)

		setSupportActionBar(main_toolbar)

		val pagerAdapter = MainPagerAdapter(this@MainActivity, supportFragmentManager)
		main_viewPager.adapter = pagerAdapter
		main_tabs.setupWithViewPager(main_viewPager)


		mainController.onCreate(this@MainActivity)
	}
}