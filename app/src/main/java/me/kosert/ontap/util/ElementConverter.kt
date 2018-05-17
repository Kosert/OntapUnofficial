package me.kosert.ontap.util

import me.kosert.ontap.data.WebRetriever.mainPageUrl
import me.kosert.ontap.model.BeerItem
import me.kosert.ontap.model.BeerState
import me.kosert.ontap.model.City
import me.kosert.ontap.model.Multitap
import org.jsoup.nodes.Element

/**
 * Created by Kosert on 2018-02-10.
 */

fun Element.toCity() : City
{
	val url = this.attr("href")
	val	nr = this.select(".badge").text().toInt()
	val name = this.childNode(0).outerHtml().trim()
	return City(name, url, nr)
}

fun Element.toMultitap() : Multitap
{
	val a = this.getElementsByTag("a")
	val name = a[1].html().stripHtml()
	val url = a[0].attr("href")
	val image = a[0].children().first().attr("src")
	return Multitap(name, url, image)
}

fun Element.toBeer(): BeerItem
{
	val panelBody = this.getElementsByClass("panel-body").first()
	val label = panelBody.select(".label")

	val number = label.eachText().first().trim()

	val badges = label.drop(1).joinToString(separator = " | ") {
		it.html()
	}

	val badgesFromatted = badges.replace("span", "font").replace("style", "color").replace("color:", "").replace(";", "")
	val brewery = this.getElementsByClass("brewery").text().stripHtml()

	val cmlShadow = this.getElementsByClass("cml_shadow")

	val nameBlock = cmlShadow.first().children().first().childNodes()
	val statsBlock = nameBlock.last()
	val statsList = statsBlock.toString().stripHtml().trim().split(" ")


	val stats = if (statsList.first().isNotEmpty()) statsList else emptyList()

	val name = nameBlock[4].outerHtml().stripHtml().trim()
	val beerStyle = cmlShadow.eachText().last().stripHtml()

	val prices = this.getElementsByClass("col-xs-5").text()
	val ibu = this.getElementsByClass("col-xs-7").select("kbd").eachText().find {
		it.contains("IBU")
	}

	val allStatistics = ibu?.let { stats.plus(ibu) } ?: stats

	val style = select(".panel-footer").attr("style")
	val color = style.substring(style.lastIndexOf(" ")+1)

	val img = panelBody.attr("style")
	val imageUrl =
			if (img.toString().isNotEmpty())
				img.split("(",")")[1]
			else ""

	val flag = cmlShadow.select("img").attr("src")
	val flagUrl =
			if (flag.toString().isNotEmpty())
				mainPageUrl + flag
			else ""

	val url = mainPageUrl + this.getElementsByClass("panel-default").attr("onclick").split("'")[1]

	return BeerItem(
			number,
			name,
			brewery,
			beerStyle,
			prices,
			color,
			imageUrl,
			flagUrl,
			badgesFromatted,
			url,
			allStatistics)
}

fun Element.toBeerState() : BeerState
{
	val name = this.getElementsByClass("cml_shadow")
			.first().children().first().childNodes()[4]
			.outerHtml().stripHtml().trim()

	val brewery = this.getElementsByClass("brewery").text().stripHtml()

	return BeerState(name, brewery)
}

fun BeerItem.toBeerState() : BeerState
{
	return BeerState(this.name, this.brewery)
}