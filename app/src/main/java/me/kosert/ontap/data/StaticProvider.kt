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

	private val logger = Logger("StaticProvider")
	private val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

	fun getGson() : Gson
	{
		return gson
	}

	fun initialize(mem: SharedPreferences, preferences: SharedPreferences)
	{
		memory = mem
		prefs = preferences
	}

	fun isPrefsNotInitialized() : Boolean
	{
		return !this::prefs.isInitialized
	}

	fun isMemoryNotInitialized() : Boolean
	{
		return !this::memory.isInitialized
	}

	object Prefs
	{
		//TODO save favorites, notification preferences etc
	}

	/**
	 * Contains saved objects
	 */
	object Memory
	{
		private const val cities_key = "CITIES"
		private const val multitap_list_prefix = "MULTITAP_LIST_"
		private const val multitap_prefix = "MULTITAP_"

		fun resetMemory()
		{
			if (isMemoryNotInitialized()) return
			memory.edit().clear().apply()
		}

		fun getCityList() : List<City>?
		{
			if (isMemoryNotInitialized()) return null

			memory.getString(cities_key, null)?.let {
				logger.i("getCityList: " + it)
				val array = gson.fromJson(it, arrayOf<City>()::class.java)
				return array.toList()
			}
			return null
		}

		fun saveCityList(list : List<City>)
		{
			if (isMemoryNotInitialized()) return

			val json = gson.toJson(list)
			logger.i("saveCityList: " + json)
			val editor = memory.edit().putString(cities_key, json)
			editor.apply()
		}

		fun getMultitapList(city: City) : List<Multitap>?
		{
			if (isMemoryNotInitialized()) return null

			memory.getString(multitap_list_prefix + city.url, null)?.let {

				val array = gson.fromJson(it, arrayOf<Multitap>()::class.java)
				return array.toList()
			}
			return null
		}

		fun saveMultitapList(city: City)
		{
			if (isMemoryNotInitialized()) return

			val json = gson.toJson(city.multitaps)
			val editor = memory.edit().putString(multitap_list_prefix + city.url, json)
			editor.apply()
		}

		fun getMultitapDetails(multitap: Multitap) : MultitapDetails?
		{
			if (isMemoryNotInitialized()) return null

			memory.getString(multitap_prefix + multitap.url, null)?.let {
				return gson.fromJson(it, MultitapDetails::class.java)
			}
			return null
		}

		fun saveMultitapDetails(multitap: Multitap)
		{
			if (isMemoryNotInitialized()) return

			val json = gson.toJson(multitap.details)
			val editor = memory.edit().putString(multitap_prefix + multitap.url, json)
			editor.apply()
		}

	}
}
