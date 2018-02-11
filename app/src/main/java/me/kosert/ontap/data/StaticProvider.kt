package me.kosert.ontap.data

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import me.kosert.ontap.model.City
import me.kosert.ontap.model.Multitap
import me.kosert.ontap.util.Logger

/**
 * Created by Kosert on 2018-02-10.
 */

object StaticProvider
{
	private lateinit var prefs : SharedPreferences

	private val logger = Logger("StaticProvider")
	private val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

	fun initialize(sharedPreferences: SharedPreferences)
	{
		prefs = sharedPreferences
	}

	fun isNotInitialized() : Boolean
	{
		return !this::prefs.isInitialized
	}

	object Prefs
	{
		//TODO save favorites, notification preferences etc
	}

	object Memory
	{
		private const val cities_key = "CITIES"
		private const val multitap_list_prefix = "MULTITAP_LIST_"
		private const val multitap_prefix = "MULTITAP_"

		fun resetMemory()
		{
			if (isNotInitialized()) return

			prefs.edit().clear().apply()
		}

		fun getCityList() : List<City>?
		{
			if (isNotInitialized()) return null

			prefs.getString(cities_key, null)?.let {
				logger.i("getCityList: " + it)
				val array = gson.fromJson(it, arrayOf<City>()::class.java)
				return array.toList()
			}
			return null
		}

		fun saveCityList(list : List<City>)
		{
			if (isNotInitialized()) return

			val json = gson.toJson(list)
			logger.i("saveCityList: " + json)
			val editor = prefs.edit().putString(cities_key, json)
			editor.apply()
		}

		fun getMultitapList(city: City) : List<Multitap>?
		{
			if (isNotInitialized()) return null

			prefs.getString(multitap_list_prefix + city.url, null)?.let {

				val array = gson.fromJson(it, arrayOf<Multitap>()::class.java)
				return array.toList()
			}
			return null
		}

		fun saveMultitapList(city: City)
		{
			if (isNotInitialized()) return

			val json = gson.toJson(city.multitaps)
			val editor = prefs.edit().putString(multitap_list_prefix + city.url, json)
			editor.apply()
		}

	}
}
