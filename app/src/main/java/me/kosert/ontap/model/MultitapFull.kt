package me.kosert.ontap.model

import com.google.gson.annotations.Expose

/**
 * Created by Kosert on 2018-02-11.
 */

class MultitapFull(
		name: String,
		url: String,
		image: String,
		@Expose
		val address: String,
		@Expose
		val website: String,
		@Expose
		val phone: String,
		@Expose
		val beerCount: Int,
		@Expose
		val coords: MutableList<String>
) : Multitap(name, url, image)
{
	val beers = mutableListOf<Beer>()

	override fun toString(): String
	{
		return "MultitapFull(address='$address', website='$website', coords=$coords, phone='$phone', beerCount=$beerCount, beers=$beers)"
	}
}