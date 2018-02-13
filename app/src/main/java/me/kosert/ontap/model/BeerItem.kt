package me.kosert.ontap.model

/**
 * Created by Kosert on 2018-02-11.
 */

class BeerItem(
		val number: String,
		val name: String,
		val brewery: String,
		val style: String,
		val prices: String,
		val color: String,
		val imageUrl: String,
		val flagUrl: String,
		val badges: String,
		val url: String,
		statistics: List<String>)
{
	val stats: MutableList<String> = mutableListOf()

	init
	{
		stats.addAll(statistics)
	}

	fun getStatsString(): String
	{
		return stats.joinToString(separator = " | ")
	}

	override fun toString(): String
	{
		return "BeerItem(number='$number', name='$name', brewery='$brewery', style='$style', prices='$prices', color='$color', imageUrl='$imageUrl', flagUrl='$flagUrl', badges='$badges', url='$url', stats=$stats)"
	}

}
