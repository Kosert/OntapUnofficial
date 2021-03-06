package me.kosert.ontap.ui.activities.settings

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.preference_led.*
import kotlinx.android.synthetic.main.preference_notification.*
import kotlinx.android.synthetic.main.preference_sound.*
import kotlinx.android.synthetic.main.preference_sync_period.*
import kotlinx.android.synthetic.main.preference_vibrate.*
import kotlinx.android.synthetic.main.settings_activity.*
import me.kosert.ontap.R
import me.kosert.ontap.data.StaticProvider
import me.kosert.ontap.ui.activities.AbstractActivity
import me.kosert.ontap.ui.activities.settings.adapters.RecyclerNotificationAdapter

/**
 * Created by Kosert on 2018-02-15.
 */

class SettingsActivity : AbstractActivity()
{
	private val settingsController = SettingsController()

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.settings_activity)

		setSupportActionBar(settings_toolbar)
		supportActionBar?.title = getString(R.string.menu_preferences)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		settings_notifications.setOnClickListener {
			settings_notifications_checkbox.toggle()
			settingsController.enableNotifications(settings_notifications_checkbox.isChecked)
		}

		settings_notifications_sound.setOnClickListener {
			settings_notifications_checkbox_sound.toggle()
			settingsController.enableSound(settings_notifications_checkbox_sound.isChecked)
		}

		settings_notifications_vibrate.setOnClickListener {
			settings_notifications_checkbox_vibrate.toggle()
			settingsController.enableVibrate(settings_notifications_checkbox_vibrate.isChecked)
		}

		settings_notifications_led.setOnClickListener {
			settings_notifications_checkbox_led.toggle()
			settingsController.enableLed(settings_notifications_checkbox_led.isChecked)
		}

		settings_clear_memory.setOnClickListener {
			settingsController.onClearMemory()
		}

		settings_about.setOnClickListener{
			settingsController.onAbout()
		}

		val adapter = RecyclerNotificationAdapter(this, StaticProvider.Favorites.favoritesList)
		settings_recycler.adapter = adapter
		settings_recycler.layoutManager = LinearLayoutManager(this)

		settings_notifications_spinner_period.adapter = ArrayAdapter<String>(
				this,
				R.layout.spinner_preference_item,
				resources.getStringArray(R.array.settings_sync_times))

		settings_notifications_spinner_period.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
		{
			override fun onNothingSelected(p0: AdapterView<*>?) {}

			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
			{
				settingsController.setSyncPeriod(position)
			}
		}


		Handler().post{
			settings_scrollview.scrollTo(0, 0)
		}

		val callbacks = object : ISettingsCallbacks
		{
			override fun notifyRecycler()
			{
				adapter.notifyDataSetChanged()
			}

			override fun setRecyclerCallback(onClickCallback: RecyclerNotificationAdapter.ItemClickCallback)
			{
				adapter.setOnClickCallback(onClickCallback)
			}

			override fun setPrefs(notifis: Boolean, sound: Boolean, vibrate: Boolean, led: Boolean, syncPosition: Int)
			{
				settings_notifications_checkbox.isChecked = notifis
				settings_notifications_checkbox_sound.isChecked = sound
				settings_notifications_checkbox_vibrate.isChecked = vibrate
				settings_notifications_checkbox_led.isChecked = led
				settings_notifications_spinner_period.setSelection(syncPosition)
			}
		}

		settingsController.onCreate(this@SettingsActivity, callbacks)
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