package me.kosert.ontap.ui.activities.main

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.main_activity.*
import me.kosert.ontap.R
import me.kosert.ontap.ui.activities.AbstractActivity
import me.kosert.ontap.ui.activities.main.adapters.MainPagerAdapter
import me.kosert.ontap.ui.activities.main.fragments.favorites.FavoritesFragment

/**
 * Created by Kosert on 2018-02-10.
 */
class MainActivity : AbstractActivity()
{
	private val mainController = MainController()

	var menuFirst : MenuItem? = null
	var menuSecond : MenuItem? = null

	override fun onCreate(savedInstanceState: Bundle?)
	{
		setTheme(R.style.AppTheme_NoActionBar)

		super.onCreate(savedInstanceState)
		setContentView(R.layout.main_activity)

		setSupportActionBar(main_toolbar)

		val pagerAdapter = MainPagerAdapter(this@MainActivity, supportFragmentManager)
		main_viewPager.adapter = pagerAdapter
		main_tabs.setupWithViewPager(main_viewPager)

		main_viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
			override fun onPageScrollStateChanged(state: Int) {}
			override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
			override fun onPageSelected(position: Int)
			{
				mainController.onPageSelected()
			}
		})

		val callbacks = object : IMainCallbacks
		{
			override var isEditMode: Boolean
				get() = !main_viewPager.isSwipingEnabled
				set(value)
				{
					main_viewPager.isSwipingEnabled = !value
					val fragment = pagerAdapter.getFragment(0) as FavoritesFragment
					fragment.recyclerTouchOptions.editEnabled = value
					pagerAdapter.setHideCities(value)
					pagerAdapter.notifyDataSetChanged()
					if (!value)
						fragment.notifyFavoritesChanged()

				}

			override var currentPage: Int
				get() = main_viewPager.currentItem
				set(value)
				{
					main_viewPager.setCurrentItem(value, true)
				}

			override fun enableBackButton(enable: Boolean)
			{
				supportActionBar?.setDisplayHomeAsUpEnabled(enable)
			}

			override fun getMenuFirstItem(): MenuItem?
			{
				return menuFirst
			}

			override fun getMenuSecondItem(): MenuItem?
			{
				return menuSecond
			}

		}

		mainController.onCreate(this@MainActivity, callbacks)
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean
	{
		menuInflater.inflate(R.menu.main_menu, menu)

		menuFirst = menu?.findItem(R.id.main_menu_first)
		menuSecond = menu?.findItem(R.id.main_menu_second)
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
			R.id.main_menu_first -> {
				mainController.onMenuFirstClicked()
				true
			}
			R.id.main_menu_second -> {
				mainController.onMenuSecondClicked()
				true
			}
			else -> {
				super.onOptionsItemSelected(item)
			}
		}
	}

	override fun onBackPressed()
	{
		if (!mainController.onBackPressed())
			super.onBackPressed()
	}
}