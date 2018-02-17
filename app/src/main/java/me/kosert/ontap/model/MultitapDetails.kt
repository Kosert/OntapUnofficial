package me.kosert.ontap.model

import com.google.gson.annotations.Expose

/**
 * Created by Kosert on 2018-02-12.
 */
class MultitapDetails(
		@Expose val address: String,
		@Expose val website: String,
		@Expose val phone: String,
		@Expose var beerCount: Int,
		@Expose val coords: Array<String>
)
{
	var invalidated : Boolean = false

	// empty constructor required by Gson
	private constructor() : this("", "", "", 0, arrayOf("", ""))
}