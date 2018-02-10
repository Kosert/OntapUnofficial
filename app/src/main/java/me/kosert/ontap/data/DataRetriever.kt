package me.kosert.ontap.data

import android.os.Handler
import me.kosert.ontap.data.callbacks.NetworkCallback
import me.kosert.ontap.model.City
import me.kosert.ontap.util.Logger
import me.kosert.ontap.util.toCity
import okhttp3.*
import org.jsoup.Jsoup
import java.io.IOException


/**
 * Created by Kosert on 2018-02-10.
 */
object DataRetriever
{
	private val mainPageUrl = "http://ontap.pl/"
	private val logger = Logger("DataRetriever")
	private val mainHandler = Handler()

	fun downloadCityList(callback: NetworkCallback)
	{
		val http = OkHttpClient()

		val request = Request.Builder()
				.url(mainPageUrl)
				.build()

		http.newCall(request).enqueue(object : Callback
		{
			override fun onFailure(call: Call?, e: IOException?)
			{
				mainHandler.post {
					logger.e(e.toString())
					callback.onFailure()
				}
			}

			override fun onResponse(call: Call?, response: Response)
			{
				val body = response.body()!!.string()

				mainHandler.post{
					val doc = Jsoup.parse(body)

					var elem = doc.getElementById("pubs").children().first().nextElementSibling().nextElementSibling()
					var next = true
					while (next)
					{
						val city = elem.toCity()
						DataProvider.cities.add(city)
						elem = elem.nextElementSibling()

						if (elem == null || !elem.`is`("button"))
						{
							next = false
						}
					}

					callback.onSuccess()
				}
			}
		})
	}

	fun downloadCityMultitaps(city: City, forceReload : Boolean)
	{
		//TODO load from dataStorage -> from json -> download city
	}



}