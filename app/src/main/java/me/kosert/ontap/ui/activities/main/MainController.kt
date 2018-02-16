package me.kosert.ontap.ui.activities.main

import android.content.Context
import android.content.Intent
import android.widget.Toast
import me.kosert.ontap.R
import me.kosert.ontap.data.StaticProvider
import me.kosert.ontap.ui.activities.settings.SettingsActivity

/**
 * Created by Kosert on 2018-02-15.
 */
class MainController
{
	private lateinit var context: Context
	private lateinit var callbacks: IMainCallbacks

	private var editMode : Boolean = false

	fun onCreate(context: Context, callbacks: IMainCallbacks)
	{
		this.context = context
		this.callbacks = callbacks
	}

	fun onPageSelected()
	{
		adjustIcons()
	}

	fun onMenuFirstClicked()
	{
		when
		{
			callbacks.currentPage == 0 && !editMode -> {
				editMode = true
				callbacks.isEditMode = true
				adjustIcons()
				StaticProvider.Favorites.stashFavorites()
			}
			callbacks.currentPage == 0 && editMode -> {
				editMode = false
				callbacks.isEditMode = false
				adjustIcons()
				StaticProvider.Favorites.saveFavorites()
			}
			else ->{
				Toast.makeText(context, R.string.toast_not_implemented, Toast.LENGTH_SHORT).show()
				//TODO MAP
			}
		}
	}

	fun onMenuSecondClicked()
	{
		when
		{
			callbacks.currentPage == 0 && !editMode ->{
				goToSettings()
			}
			callbacks.currentPage == 0 && editMode ->{
				Toast.makeText(context, R.string.toast_not_implemented, Toast.LENGTH_SHORT).show()
				//TODO display help dialog
			}
			else ->{
				goToSettings()
			}
		}
	}

	fun onBackPressed(): Boolean
	{
		return when
		{
			callbacks.currentPage == 0 && !editMode ->{
				false
			}
			callbacks.currentPage == 0 && editMode ->{
				//TODO Cancel Dialog
				StaticProvider.Favorites.unstashFavorites()?.let {
					StaticProvider.Favorites.favoritesList.clear()
					StaticProvider.Favorites.favoritesList.addAll(it)
				}
				editMode = false
				callbacks.isEditMode = false
				adjustIcons()
				true
			}
			else ->{
				callbacks.currentPage = 0
				true
			}
		}
	}

	private fun adjustIcons()
	{
		when
		{
			callbacks.currentPage == 0 && !editMode ->
			{
				callbacks.enableBackButton(false)
				val first = callbacks.getMenuFirstItem()
				first?.title = context.getString(R.string.menu_edit)
				first?.setIcon(R.drawable.ic_mode_edit_24dp)

				val second = callbacks.getMenuSecondItem()
				second?.title = context.getString(R.string.menu_preferences)
				second?.setIcon(R.drawable.ic_settings_24dp)
			}
			callbacks.currentPage == 0 && editMode ->
			{
				callbacks.enableBackButton(true)
				val first = callbacks.getMenuFirstItem()
				first?.title = context.getString(R.string.menu_save)
				first?.setIcon(R.drawable.ic_save_24dp)

				val second = callbacks.getMenuSecondItem()
				second?.title = context.getString(R.string.menu_help)
				second?.setIcon(R.drawable.ic_help_24dp)
			}
			else ->
			{
				callbacks.enableBackButton(true)
				val first = callbacks.getMenuFirstItem()
				first?.title = context.getString(R.string.menu_map)
				first?.setIcon(R.drawable.ic_map_24dp)

				val second = callbacks.getMenuSecondItem()
				second?.title = context.getString(R.string.menu_preferences)
				second?.setIcon(R.drawable.ic_settings_24dp)
			}
		}
	}

	private fun goToSettings()
	{
		val intent = Intent(context, SettingsActivity::class.java)
		context.startActivity(intent)
	}
}