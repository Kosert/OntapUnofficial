package me.kosert.ontap.util

import me.kosert.ontap.model.City
import me.kosert.ontap.model.Multitap
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

fun Element.toMultitap() : Multitap
{
	val a = this.getElementsByTag("a")
	val name = a[1].html()
	val url = a[0].attr("href")
	val image = a[0].children().first().attr("src")
	return Multitap(name, url, image)
}