package me.kosert.ontap.util

import me.kosert.ontap.model.City
import org.jsoup.nodes.Element

/**
 * Created by Kosert on 2018-02-10.
 */

fun Element.toCity() : City
{
	val url = this.attr("onclick").split("'", "'")[1]
	val	nr = this.select(".badge").text().toInt()
	val name = this.select(".btn").first().childNode(0).outerHtml().trim()
	return City(name, url, nr)
}
