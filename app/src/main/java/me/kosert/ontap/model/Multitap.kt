package me.kosert.ontap.model

import com.google.gson.annotations.Expose

/**
 * Created by Kosert on 2018-02-10.
 */
open class Multitap(
		@Expose
		val name: String,
		@Expose
		val url: String,
		@Expose
		val image: String
)
{
	// empty constructor required by Gson
	private constructor() : this("", "", "")

	fun toFull(address: String, website: String, phone: String, beerCount: Int, coords: MutableList<String>): MultitapFull
	{
		return MultitapFull(name, url, image, address, website, phone, beerCount, coords)
	}

	override fun toString(): String
	{
		return "Multitap(name='$name', url='$url', image='$image')"
	}
}