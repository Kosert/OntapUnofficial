package me.kosert.ontap

import android.app.Application
import android.content.Context
import com.squareup.picasso.Picasso
import me.kosert.ontap.data.StaticProvider


/**
 * Created by Kosert on 2018-02-10.
 */

class OntapApplication : Application()
{
	override fun onCreate()
	{
		super.onCreate()

		if (StaticProvider.DEBUG)
		{
			val picasso = Picasso.Builder(applicationContext)
					.indicatorsEnabled(true)
					.build()

			Picasso.setSingletonInstance(picasso)
		}

		val memory = getSharedPreferences(getString(R.string.memory_key), Context.MODE_PRIVATE)
		val prefs = getSharedPreferences(getString(R.string.preference_key), Context.MODE_PRIVATE)
		val favs = getSharedPreferences(getString(R.string.favorites_key), Context.MODE_PRIVATE)
		StaticProvider.initializeMemory(memory)
		StaticProvider.initializePrefs(prefs)
		StaticProvider.initializeFavorites(favs)

		StaticProvider.Prefs.setDefaultPreferences(false)
		StaticProvider.Favorites.loadFavorites()
		StaticProvider.NotificationMemory.loadNotifications()
	}
}