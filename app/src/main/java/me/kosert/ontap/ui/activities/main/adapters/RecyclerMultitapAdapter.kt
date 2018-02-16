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
import kotlinx.android.synthetic.main.listview_multitap_row.view.*
import me.kosert.ontap.R
import me.kosert.ontap.model.Multitap

/**
 * Created by Kosert on 2018-02-11.
 */

private const val itemViewId = R.layout.listview_multitap_row

class RecyclerMultitapAdapter(val context: Context, val list: MutableList<Multitap>) : RecyclerView.Adapter<RecyclerMultitapAdapter.ItemHolder>(), ItemTouchHelperCallbacks
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

	override fun onItemRemove(position: Int)
	{
		list.removeAt(position)
		notifyItemRemoved(position)
	}

	override fun onItemMove(fromPosition: Int, toPosition: Int) : Boolean
	{
		if (fromPosition < toPosition)
		{
			for (i in fromPosition until toPosition)
			{
				java.util.Collections.swap(list, i, i + 1)
			}
		}
		else
		{
			for (i in fromPosition downTo toPosition + 1)
			{
				java.util.Collections.swap(list, i, i - 1)
			}
		}
		notifyItemMoved(fromPosition, toPosition)
		return true
	}

	inner class ItemHolder(v: View) : RecyclerView.ViewHolder(v)
	{
		private lateinit var multitap : Multitap
		private val icon: ImageView = v.item_multitap_image
		private val name: TextView = v.item_multitap_text_big
		private val address: TextView = v.item_multitap_text_small
		private val taps : TextView = v.item_multitap_text_taps
		private val progress : ProgressBar = v.item_multitap_loading_taps

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