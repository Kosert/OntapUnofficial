package me.kosert.ontap.model

/**
 * Created by Kosert on 2018-02-10.
 */
data class Multitap(val name: String, val url: String, val image: String)
{
	var address: String = ""
	var website: String = ""
	val coords = mutableListOf<String>()
	var phone: String = ""
	var beers = -1
}