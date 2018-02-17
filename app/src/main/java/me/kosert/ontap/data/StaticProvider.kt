package me.kosert.ontap.data

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import me.kosert.ontap.model.City
import me.kosert.ontap.model.Multitap
import me.kosert.ontap.model.MultitapDetails
import me.kosert.ontap.util.Logger

/**
 * Created by Kosert on 2018-02-10.
 */

object StaticProvider
{
	const val DEBUG = true

	private lateinit var prefs: SharedPreferences
	private lateinit var memory: SharedPreferences
	private lateinit var favorites: SharedPreferences

	private val logger = Logger("StaticProvider")
	private val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

	fun getGson() : Gson
	{
		return gson
	}

	fun initializePrefs(preferences: SharedPreferences)
	{
		prefs = preferences
	}

	fun initializeFavorites(favs : SharedPreferences)
	{
		favorites = favs
	}

	fun initializeMemory(mem: SharedPreferences)
	{
		memory = mem
	}

	fun isPrefsNotInitialized() : Boolean
	{
		return !this::prefs.isInitialized
	}

	fun isFavoritesNotInitialized() : Boolean
	{
		return !this::favorites.isInitialized
	}

	fun isMemoryNotInitialized() : Boolean
	{
		return !this::memory.isInitialized
	}

	object Prefs
	{
		enum class PrefType(val prefKey : String) {
			NOTIFICATIONS_KEY("ENABLE_NOTIFICATIONS"),
			SOUND_KEY("ENABLE_SOUND"),
			VIBRATE_KEY("ENABLE_VIBRATE"),
			LED_KEY("ENABLE_LED"),
		}

		fun setDefaultPreferences(force: Boolean)
		{
			if (isPrefsNotInitialized()) return

			val editor = prefs.edit()

			if (force)
			{
				editor.putBoolean(PrefType.NOTIFICATIONS_KEY.prefKey, false)
				editor.putBoolean(PrefType.SOUND_KEY.prefKey, true)
				editor.putBoolean(PrefType.VIBRATE_KEY.prefKey, true)
				editor.putBoolean(PrefType.LED_KEY.prefKey, true)
				editor.apply()

				return
			}

			if (!prefs.contains(PrefType.NOTIFICATIONS_KEY.prefKey))
				editor.putBoolean(PrefType.NOTIFICATIONS_KEY.prefKey, false)
			if (!prefs.contains(PrefType.SOUND_KEY.prefKey))
				editor.putBoolean(PrefType.SOUND_KEY.prefKey, true)
			if (!prefs.contains(PrefType.VIBRATE_KEY.prefKey))
				editor.putBoolean(PrefType.VIBRATE_KEY.prefKey, true)
			if (!prefs.contains(PrefType.LED_KEY.prefKey))
				editor.putBoolean(PrefType.LED_KEY.prefKey, true)

			editor.apply()
		}

		fun getPrefBoolean(prefType : PrefType) : Boolean?
		{
			if (isPrefsNotInitialized()) return null

			return if(prefs.contains(prefType.prefKey))
			{
				prefs.getBoolean(prefType.prefKey, false)
			}
			else null
		}

		fun setPrefBoolean(prefType: PrefType, value: Boolean)
		{
			if (isPrefsNotInitialized()) return

			prefs.edit().putBoolean(prefType.prefKey, value).apply()
		}
	}

	object Favorites
	{
		private const val FAVORITES_KEY = "FAVORITES_LIST"
		private const val FAVORITES_STASH_KEY = "FAVORITES_STASHED_LIST"

		val favoritesList = mutableListOf<Multitap>()

		fun loadFavorites()
		{
			if (isFavoritesNotInitialized()) return

			val json = favorites.getString(FAVORITES_KEY, null)
			json?.let {
				val list = gson.fromJson(it, arrayOf<Multitap>()::class.java)
				favoritesList.clear()
				favoritesList.addAll(list)
			}
		}

		fun isFavorite(multitap: Multitap) : Boolean
		{
			return !favoritesList.none {
				it.url == multitap.url
			}
		}

		fun addFavorite(multitap: Multitap)
		{
			favoritesList.add(multitap)
			saveFavorites()
		}

		fun removeFavorite(multitap: Multitap)
		{
			favoritesList.removeAll { x -> x.url == multitap.url }
			saveFavorites()
		}

		fun saveFavorites()
		{
			if (isFavoritesNotInitialized()) return

			val editor = favorites.edit()
			val json = gson.toJson(favoritesList)
			editor.putString(FAVORITES_KEY, json)
			editor.apply()
			syncNotificationsWithFavorites()
		}

		fun stashFavorites()
		{
			if (isFavoritesNotInitialized()) return

			val editor = favorites.edit()
			val json = gson.toJson(favoritesList)
			editor.putString(FAVORITES_STASH_KEY, json)
			editor.apply()
		}

		fun unstashFavorites() : Array<Multitap>?
		{
			if (isFavoritesNotInitialized()) return null

			val json = favorites.getString(FAVORITES_STASH_KEY, null)
			return json?.let {
				gson.fromJson(it, arrayOf<Multitap>()::class.java)
			}
		}

		private const val NOTIFICATIONS_KEY = "NOTIFICATIONS_LIST"

		private val notificationList = mutableListOf<Multitap>()

		fun isNotificationEnabled(multitap: Multitap) : Boolean
		{
			return !notificationList.none {
				it.url == multitap.url
			}
		}

		fun addNotification(multitap: Multitap)
		{
			notificationList.add(multitap)
			saveNotifications()
		}

		fun removeNotification(multitap: Multitap)
		{
			notificationList.removeAll { x -> x.url == multitap.url }
			saveNotifications()
		}

		fun saveNotifications()
		{
			if (isFavoritesNotInitialized()) return

			val editor = favorites.edit()
			val json = gson.toJson(notificationList)
			editor.putString(NOTIFICATIONS_KEY, json)
			editor.apply()
		}

		fun loadNotifications()
		{
			if (isFavoritesNotInitialized()) return

			val json = favorites.getString(NOTIFICATIONS_KEY, null)
			json?.let {
				val list = gson.fromJson(it, arrayOf<Multitap>()::class.java)
				notificationList.clear()
				notificationList.addAll(list)
			}
		}

		private fun syncNotificationsWithFavorites()
		{
			notificationList.forEach {

				val isInFavorites = favoritesList.any {
					x -> x.url == it.url
				}
				if (!isInFavorites)
					removeNotification(it)
			}
		}
	}

	/**
	 * Contains saved objects
	 */
	object Memory
	{
		private const val CITIES_KEY = "CITIES"
		private const val MULTITAP_LIST_PREFIX = "MULTITAP_LIST_"
		private const val MULTITAP_PREFIX = "MULTITAP_"

		fun resetMemory()
		{
			if (isMemoryNotInitialized()) return

			DataProvider.clearMap()
			memory.edit().clear().apply()
		}

		fun getCityList() : List<City>?
		{
			if (isMemoryNotInitialized()) return null

			logger.i("loading City List from memory")
			memory.getString(CITIES_KEY, null)?.let {
				val array = gson.fromJson(it, arrayOf<City>()::class.java)
				return array.toList()
			}
			return null
		}

		fun saveCityList(list : List<City>)
		{
			if (isMemoryNotInitialized()) return

			logger.i("saving City List to memory")
			val json = gson.toJson(list)
			val editor = memory.edit().putString(CITIES_KEY, json)
			editor.apply()
		}

		fun getMultitapList(city: City) : List<Multitap>?
		{
			if (isMemoryNotInitialized()) return null

			logger.i("loading Multitap List from memory")
			memory.getString(MULTITAP_LIST_PREFIX + city.url, null)?.let {

				val array = gson.fromJson(it, arrayOf<Multitap>()::class.java)
				return array.toList()
			}
			return null
		}

		fun saveMultitapList(city: City)
		{
			if (isMemoryNotInitialized()) return

			logger.i("saving Multitap List to memory")
			val json = gson.toJson(city.multitaps)
			val editor = memory.edit().putString(MULTITAP_LIST_PREFIX + city.url, json)
			editor.apply()
		}

		fun getMultitapDetails(multitap: Multitap) : MultitapDetails?
		{
			if (isMemoryNotInitialized()) return null

			logger.i("loading Multitap Details from memory")
			memory.getString(MULTITAP_PREFIX + multitap.url, null)?.let {
				return gson.fromJson(it, MultitapDetails::class.java)
			}
			return null
		}

		fun saveMultitapDetails(multitap: Multitap)
		{
			if (isMemoryNotInitialized()) return

			logger.i("saving Multitap Details to memory")
			val json = gson.toJson(multitap.details)
			val editor = memory.edit().putString(MULTITAP_PREFIX + multitap.url, json)
			editor.apply()
		}

	}
}
