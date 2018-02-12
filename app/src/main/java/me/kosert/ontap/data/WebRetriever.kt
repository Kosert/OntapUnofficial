package me.kosert.ontap.data

import android.os.Handler
import me.kosert.ontap.data.callbacks.NetworkCallback
import me.kosert.ontap.model.City
import me.kosert.ontap.model.Multitap
import me.kosert.ontap.model.MultitapDetails
import me.kosert.ontap.util.Logger
import me.kosert.ontap.util.toCity
import me.kosert.ontap.util.toMultitap
import me.kosert.ontap.util.toPair
import okhttp3.*
import org.jsoup.Jsoup
import java.io.IOException


/**
 * Created by Kosert on 2018-02-10.
 */
object WebRetriever
{
	private val mainPageUrl = "http://ontap.pl/"
	private val logger = Logger("WebRetriever")
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
				val doc = Jsoup.parse(body)

				var elem = doc.getElementById("pubs").children().first().nextElementSibling().nextElementSibling()

				DataProvider.cities.clear()
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

				mainHandler.post {
					callback.onSuccess()
				}
			}
		})
	}

	fun downloadCityMultitaps(city: City, callback: NetworkCallback)
	{
		val http = OkHttpClient()

		val request = Request.Builder()
				.url(city.url)
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
				val doc = Jsoup.parse(body)

				var elem = doc.getElementsByClass("row").first().children().first()

				city.multitaps.clear()
				var next = true
				while (next)
				{
					val multitap = elem.toMultitap()
					city.multitaps.add(multitap)
					elem = elem.nextElementSibling()

					if (elem == null || !elem.`is`("div"))
					{
						next = false
					}
				}

				mainHandler.post{
					callback.onSuccess()
				}
			}
		})
	}

	fun downloadMultitapDetails(multitap: Multitap, callback: NetworkCallback)
	{
		val http = OkHttpClient()

		val request = Request.Builder()
				.url(multitap.url)
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
				val doc = Jsoup.parse(body)

				val addressBlock = doc.getElementsByTag("address").first()
				val adr = addressBlock.children()[0].nextSibling().outerHtml().split(",").dropLast(1).joinToString(",").trim()

				val a = addressBlock.getElementsByTag("a")
				val coords =
						if (a.size > 0)
							a.first().attr("href").substringAfter("=").split(",").toPair()
						else Pair("","")

				val website =
						if(a.size > 1)
							addressBlock.select("a")[1].attr("href")
						else ""

				val phone =
						if(a.size > 2)
							addressBlock.select("a")[2].attr("href")
						else ""

				val beerCount = doc.getElementsByClass("row")[1].children().size
				multitap.details = MultitapDetails(adr, website, phone, beerCount, coords)

				mainHandler.post{
					callback.onSuccess()
				}
			}
		})
	}
}