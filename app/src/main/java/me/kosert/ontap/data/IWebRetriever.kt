package me.kosert.ontap.data

import me.kosert.ontap.data.callbacks.BeerStatesCallback
import me.kosert.ontap.data.callbacks.NetworkCallback
import me.kosert.ontap.model.City
import me.kosert.ontap.model.Multitap

/**
 * Source of remote data.
 * Created by Kosert on 2018-04-16.
 */
interface IWebRetriever {

	/**
	 * Main page URL adress
	 */
	val mainPageUrl : String

	/**
	 * Initialize WebRetriever.
	 * @param languageTag
	 * Language tag that will be used for
	 * "Accept-Language" header in HTTP requests
	 */
	fun init(languageTag : String)

	/**
	 * Downloads the main city list
	 * @param callback
	 * Calls [NetworkCallback.onSuccess] when download completed
	 * or [NetworkCallback.onFailure] if download fails.
	 */
	fun downloadCityList(callback: NetworkCallback)

	/**
	 * Downloads list of multitaps from the given city.
	 * @param city
	 * [City] which [City.multitaps] will be populated
	 * @param callback
	 * Calls [NetworkCallback.onSuccess] when download completed
	 * or [NetworkCallback.onFailure] if download fails.
	 */
	fun downloadCityMultitaps(city: City, callback: NetworkCallback)

	/**
	 * Downloads details of the given multitap.
	 * @param multitap
	 * [Multitap] which [Multitap.details] will be updated
	 * @param callback
	 * Calls [NetworkCallback.onSuccess] when download completed
	 * or [NetworkCallback.onFailure] if download fails.
	 * @param parseBeerList
	 * If true also updates [Multitap.beers] of [multitap]
	 */
	fun downloadMultitapDetails(multitap: Multitap, callback: NetworkCallback, parseBeerList: Boolean)

	/**
	 * Downloads list of beer states for the given multitap
	 * @param multitap
	 * [Multitap] which beer states will be downloaded
	 * @param callback
	 * Calls [BeerStatesCallback.onSuccess] when download completed
	 * or [BeerStatesCallback.onFailure] if download fails.
	 */
	fun downloadMultitapBeerStates(multitap: Multitap, callback: BeerStatesCallback)
}