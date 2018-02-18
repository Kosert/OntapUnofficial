package me.kosert.ontap.ui.activities.settings.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.listview_notification_setting_row.view.*
import me.kosert.ontap.R
import me.kosert.ontap.data.StaticProvider
import me.kosert.ontap.model.Multitap

/**
 * Created by Kosert on 2018-02-18.
 */

private const val itemViewId = R.layout.listview_notification_setting_row

class RecyclerNotificationAdapter(val context: Context, val list: MutableList<Multitap>) : RecyclerView.Adapter<RecyclerNotificationAdapter.ItemHolder>()
{
	override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemHolder
	{
		val inflatedView = LayoutInflater.from(context).inflate(itemViewId, parent, false)
		return ItemHolder(inflatedView)
	}

	override fun getItemCount(): Int = list.size

	override fun onBindViewHolder(holder: ItemHolder?, position: Int)
	{
		val multitap = list[position]
		holder?.bind(multitap)
	}

	private var callback: ItemClickCallback? = null

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
		private val name: TextView = v.settings_notifications_multitap_name
		private val icon: ImageView = v.settings_notifications_multitap_icon

		fun bind(m: Multitap)
		{
			multitap = m

			name.text = multitap.name
			itemView.setOnClickListener {
				callback?.onItemClicked(multitap)
			}

			if (StaticProvider.NotificationMemory.isNotificationEnabled(multitap))
			{
				icon.setImageDrawable(context.getDrawable(R.drawable.ic_notification_on_24dp))
				icon.clearColorFilter()
			}
			else
			{
				icon.setImageDrawable(context.getDrawable(R.drawable.ic_notification_off_24dp))
				icon.setColorFilter(R.color.colorPrimaryLight)
			}
		}
	}
}