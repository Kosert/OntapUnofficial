package me.kosert.ontap.ui.activities.main

import android.content.Context
import android.content.Intent
import android.widget.Toast
import me.kosert.ontap.R
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

	fun onPageSelected(position: Int)
	{
		when(position)
		{
			0 -> {
				callbacks.enableBackButton(false)
				val first = callbacks.getMenuFirstItem()
				first?.title = context.getString(R.string.menu_edit)
				first?.setIcon(R.drawable.ic_mode_edit_24dp)

				val second = callbacks.getMenuSecondItem()
				second?.title = context.getString(R.string.menu_preferences)
				second?.setIcon(R.drawable.ic_settings_24dp)
			}
			1 -> {
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

	fun onMenuFirstClicked()
	{
		when
		{
			callbacks.currentPage == 0 && !editMode -> {
				Toast.makeText(context, R.string.toast_not_implemented, Toast.LENGTH_SHORT).show()
				//TODO EDIT MODE
			}
			callbacks.currentPage == 0 && editMode -> {
				Toast.makeText(context, R.string.toast_not_implemented, Toast.LENGTH_SHORT).show()
				//TODO SAVE
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
				//TODO ?
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
				Toast.makeText(context, R.string.toast_not_implemented, Toast.LENGTH_SHORT).show()
				//TODO exit favorites
				true
			}
			else ->{
				callbacks.currentPage = 0
				true
			}
		}
	}

	private fun goToSettings()
	{
		val intent = Intent(context, SettingsActivity::class.java)
		context.startActivity(intent)
	}
}