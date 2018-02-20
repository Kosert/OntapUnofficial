package me.kosert.ontap.data

import android.os.Handler
import me.kosert.ontap.data.callbacks.BeerStatesCallback
import me.kosert.ontap.data.callbacks.NetworkCallback
import me.kosert.ontap.model.City
import me.kosert.ontap.model.Multitap
import me.kosert.ontap.model.MultitapDetails
import me.kosert.ontap.util.*
import okhttp3.*
import org.jsoup.Jsoup
import java.io.IOException


/**
 * Created by Kosert on 2018-02-10.
 */
object WebRetriever
{
	private val logger = Logger("WebRetriever")
	private val mainHandler = Handler()

	const val mainPageUrl = "http://ontap.pl/"

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

	fun downloadMultitapDetails(multitap: Multitap, callback: NetworkCallback, parseBeerList: Boolean)
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

				parseMultitapPage(multitap, body, parseBeerList)

				mainHandler.post{
					callback.onSuccess()
				}
			}
		})
	}

	private fun parseMultitapPage(multitap: Multitap, body : String, doBeerList: Boolean)
	{
		val doc = Jsoup.parse(body)

		val addressBlock = doc.getElementsByTag("address").first()
		val adr = addressBlock.children()[0].nextSibling().outerHtml().split(",").dropLast(1).joinToString(",").trim()

		val faMapMarker = doc.getElementsByClass("fa-map-marker")
		val coords = if(faMapMarker.size > 1)
			faMapMarker.first().parent().attr("href").substringAfter("=").split(",").toTypedArray()
		else
			arrayOf("", "")

		val faGlobe = doc.getElementsByClass("fa-globe")
		val website =
				if(faGlobe.size > 0)
					faGlobe.first().parent().attr("href")
				else ""

		val faPhone = doc.getElementsByClass("fa-phone")
		val phone =
				if(faPhone.size > 0)
					faPhone.first().parent().attr("href")
				else ""

		val faMessenger = doc.getElementsByClass("fa-commenting")
		val messenger =
				if(faMessenger.size > 0)
					faMessenger.first().parent().attr("href")
				else ""

		val beerElemets = doc.getElementsByClass("row")[1].children().dropLast(1)
		val beerCount = beerElemets.size

		multitap.details = MultitapDetails(adr, website, phone, messenger, beerCount, coords)

		if (doBeerList)
		{
			val beerList = beerElemets.map {
				val beer = it.toBeer()
				logger.i(beer.toString())
				return@map beer
			}

			if (StaticProvider.NotificationMemory.isNotificationEnabled(multitap))
			{
				val beerStates = beerList.map {
					it.toBeerState()
				}
				StaticProvider.NotificationMemory.saveLastMultitapState(multitap, beerStates)
			}

			multitap.beers.clear()
			multitap.beers.addAll(beerList)
		}
	}

	fun downloadMultitapBeerStates(multitap: Multitap, callback: BeerStatesCallback)
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
				val beerElemets = doc.getElementsByClass("row")[1].children().dropLast(1)

				val list = beerElemets.map {
					it.toBeerState()
				}

				Logger.list(list)

				mainHandler.post{
					callback.onSuccess(list)
				}
			}
		})
	}
}