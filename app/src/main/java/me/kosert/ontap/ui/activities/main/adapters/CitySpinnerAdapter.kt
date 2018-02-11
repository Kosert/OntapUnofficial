package me.kosert.ontap.ui.activities.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.spinner_city_item.view.*
import me.kosert.ontap.R
import me.kosert.ontap.model.City

/**
 * Created by Kosert on 2018-02-10.
 */

private const val itemViewId = R.layout.spinner_city_item

class CitySpinnerAdapter(context: Context, private val list: List<City>) : ArrayAdapter<City>(context, itemViewId, list)
{
	inner class CityHolder(val textCity: TextView, val textNumber: TextView)

	override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
	{
		val cityHolder : CityHolder
		val layout : View

		if (convertView == null)
		{
			layout = LayoutInflater.from(context).inflate(itemViewId, parent, false)

			cityHolder = CityHolder(layout.spinner_text_city, layout.spinner_text_number)
			layout.tag = cityHolder
		}
		else
		{
			layout = convertView
			cityHolder = layout.tag as CityHolder
		}

		val city = list.get(position)
		cityHolder.textCity.text = city.name
		cityHolder.textNumber.text =
				if(city.nr > 0) city.nr.toString()
				else ""

		return layout
	}

	override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View
	{
		return getView(position, convertView, parent)
	}
}