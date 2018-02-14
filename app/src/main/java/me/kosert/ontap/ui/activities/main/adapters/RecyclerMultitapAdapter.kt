package me.kosert.ontap.ui.activities.main.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.squareup.picasso.Picasso
import me.kosert.ontap.R
import me.kosert.ontap.model.Multitap

/**
 * Created by Kosert on 2018-02-11.
 */

private const val itemViewId = R.layout.listview_multitap_row

class RecyclerMultitapAdapter(val context: Context, val list: MutableList<Multitap>) : RecyclerView.Adapter<RecyclerMultitapAdapter.ItemHolder>()
{
	private var callback: ItemClickCallback? = null

	override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemHolder {
		val inflatedView = LayoutInflater.from(parent?.context).inflate(itemViewId, parent, false)
		return ItemHolder(inflatedView)
	}

	override fun getItemCount(): Int = list.size

	override fun onBindViewHolder(holder: ItemHolder?, position: Int)
	{
		val multitap = list[position]
		holder?.bind(multitap)
	}

	fun setOnClickCallback(onClickCallback: ItemClickCallback)
	{
		callback = onClickCallback
	}

	interface ItemClickCallback
	{
		fun onItemClicked(multitap: Multitap)
	}

	inner class ItemHolder(v: View) : RecyclerView.ViewHolder(v)
	{
		private lateinit var multitap : Multitap
		private val icon: ImageView = v.findViewById(R.id.item_multitap_image) as ImageView
		private val name: TextView = v.findViewById(R.id.item_multitap_text_big) as TextView
		private val address: TextView = v.findViewById(R.id.item_multitap_text_small) as TextView
		private val taps : TextView = v.findViewById(R.id.item_multitap_text_taps) as TextView
		private val progress : ProgressBar = v.findViewById(R.id.item_multitap_loading_taps) as ProgressBar

		fun bind(m: Multitap)
		{
			multitap = m

			Picasso.with(context).load(multitap.image).into(icon)
			name.text = multitap.name

			itemView.setOnClickListener {
				callback?.onItemClicked(multitap)
			}

			multitap.details?.let {
				address.text = it.address
				taps.text = context.getString(R.string.taps, it.beerCount)
				progress.visibility = View.GONE
			} ?: run {
				address.text = ""
				progress.visibility = View.VISIBLE
				taps.text = ""
			}
		}
	}
}