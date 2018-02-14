package me.kosert.ontap.ui.activities.main.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.listview_beer_row.view.*
import me.kosert.ontap.R
import me.kosert.ontap.model.BeerItem
import me.kosert.ontap.ui.activities.main.adapters.RecyclerBeerAdapter.ItemHolder
import me.kosert.ontap.util.fromHtml
import me.kosert.ontap.util.toColor

/**
 * Created by Kosert on 2018-02-14.
 */

private const val itemViewId = R.layout.listview_beer_row

class RecyclerBeerAdapter(val context: Context, val list: MutableList<BeerItem>) : RecyclerView.Adapter<ItemHolder>()
{
	override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemHolder{
		val inflatedView = LayoutInflater.from(parent?.context).inflate(itemViewId, parent, false)
		return ItemHolder(inflatedView)
	}

	override fun getItemCount(): Int = list.size

	override fun onBindViewHolder(holder: ItemHolder?, position: Int)
	{
		val beer = list[position]
		holder?.bind(beer)
	}

	private var callback: ItemClickCallback? = null

	fun setOnClickCallback(onClickCallback: ItemClickCallback)
	{
		callback = onClickCallback
	}

	interface ItemClickCallback
	{
		fun onItemClicked(beerItem: BeerItem)
	}

	inner class ItemHolder(v: View) : RecyclerView.ViewHolder(v)
	{
		private lateinit var beer : BeerItem
		private val image = v.item_beer_image
		private val name = v.item_beer_text_Title
		private val brewery = v.item_beer_text_Brewery
		private val style = v.item_beer_text_Style
		private val prices = v.item_beer_text_prices
		private val stats = v.item_beer_text_stats
		private val number = v.item_beer_text_number
		private val time = v.item_beer_text_time
		private val flag = v.item_beer_image_flag
		private val color = v.item_beer_image_color

		fun bind(b : BeerItem)
		{
			beer = b
			if (beer.imageUrl.isNotEmpty())
			{
				image.layoutParams.width = 250
				Picasso.with(context).load(beer.imageUrl).into(image)
			}
			else
			{
				image.layoutParams.width = RelativeLayout.LayoutParams.WRAP_CONTENT
				image.setImageResource(android.R.color.transparent)
			}

			if (beer.flagUrl.isNotEmpty())
				Picasso.with(context).load(beer.flagUrl).into(flag)
			else
				flag.setImageResource(android.R.color.transparent)

			beer.color.toColor()?.let {

				color.setColorFilter(it)
				if (beer.getStatsString().isNotEmpty())
					stats.text = String.format("| %s", beer.getStatsString())

			} ?: run {
				color.clearColorFilter()
				stats.text = beer.getStatsString()
			}

			number.text = beer.number

			time.text = beer.badges.fromHtml()

			name.text = beer.name
			brewery.text = beer.brewery
			style.text = beer.style
			prices.text = beer.prices

			itemView.setOnClickListener {
				callback?.onItemClicked(b)
			}
		}
	}
}