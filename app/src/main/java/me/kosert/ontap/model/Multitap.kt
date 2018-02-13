package me.kosert.ontap.model

import com.google.gson.annotations.Expose

/**
 * Created by Kosert on 2018-02-10.
 */
class Multitap(
		@Expose val name: String,
		@Expose val url: String,
		@Expose val image: String
)
{
	@Expose
	var details : MultitapDetails? = null

	var detailsLoading : Boolean = false

	val beers = mutableListOf<BeerItem>()

	// empty constructor required by Gson
	private constructor() : this("", "", "")
}