package me.kosert.ontap.model

import com.google.gson.annotations.Expose

/**
 * Created by Kosert on 2018-02-10.
 */

class City(@Expose val name: String,
		   @Expose val url : String,
		   @Expose val nr : Int)
{

	// empty constructor required by Gson
	private constructor() : this("", "", -1)

	val multitaps = mutableListOf<Multitap>()

	override fun toString(): String
	{
		return "City(name='$name', url='$url', nr=$nr)"
	}
}