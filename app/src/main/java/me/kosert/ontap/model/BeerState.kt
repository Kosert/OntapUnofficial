package me.kosert.ontap.model

import com.google.gson.annotations.Expose

/**
 * Created by Kosert on 2018-02-18.
 */
class BeerState(
		@Expose val name: String,
		@Expose val brewery: String
)
{
	override fun toString(): String
	{
		return "BeerState(name='$name', brewery='$brewery')"
	}

	fun equals(other: BeerState): Boolean
	{
		// ignore empty taps
		if(name == "" || brewery == "")
			return true

		if (name != other.name) return false
		if (brewery != other.brewery) return false

		return true
	}
}
