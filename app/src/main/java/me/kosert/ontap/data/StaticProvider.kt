package me.kosert.ontap.data

import android.content.SharedPreferences
import com.google.gson.Gson
import me.kosert.ontap.model.City
import me.kosert.ontap.model.Multitap
import me.kosert.ontap.util.Logger

/**
 * Created by Kosert on 2018-02-10.
 */

object StaticProvider
{
	private lateinit var prefs : SharedPreferences
	var initialized = false
		private set(value){field = value}

	private val logger = Logger("StaticProvider")
	private val gson = Gson()

	fun initialize(sharedPreferences: SharedPreferences)
	{
		prefs = sharedPreferences
		initialized = true
	}


	object Memory
	{
		private val cities_key = "CITIES"
		private val city_prefix = "CITY_"
		private val multitap_prefix = "MULTITAP_"

		fun getCityList() : List<City>?
		{
			val json = prefs.getString(cities_key, null)
			val array = gson.fromJson(json, arrayOf<City>()::class.java)
			return array.toList()
		}

		fun saveCityList(list : List<City>)
		{
			val json = gson.toJson(list)
			val editor = prefs.edit().putString(cities_key, json)
			editor.apply()
		}

		fun getMultitap(url : String): Multitap?
		{
			val key = multitap_prefix + url
			val json = prefs.getString(key, null)
			return gson.fromJson(json, Multitap::class.java)
		}

	}
}
