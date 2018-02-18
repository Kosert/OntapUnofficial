package me.kosert.ontap.ui.dialogs

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Button
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.multitap_info_dialog.view.*
import me.kosert.ontap.R
import me.kosert.ontap.data.StaticProvider
import me.kosert.ontap.model.Multitap
import me.kosert.ontap.ui.activities.multitap.MultitapController


/**
 * Created by Kosert on 2018-02-17.
 */
class InfoDialogFragment : DialogFragment()
{
	lateinit var multitapController : MultitapController

	private lateinit var multitap: Multitap

	private lateinit var buttonStar : Button
	private lateinit var buttonNotification : Button

	companion object
	{
		private const val ARG_MULTITAP = "MULTITAP_KEY"

		fun newInstance(multitap: Multitap): InfoDialogFragment
		{
			val f = InfoDialogFragment()
			val args = Bundle()

			val json = StaticProvider.getGson().toJson(multitap)
			args.putString(ARG_MULTITAP, json)
			f.arguments = args

			return f
		}
	}

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		isCancelable = true


		val json = arguments.getString(ARG_MULTITAP)
		multitap = StaticProvider.getGson().fromJson(json, Multitap::class.java)
	}

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
	{
		val view : View = inflater?.inflate(R.layout.multitap_info_dialog, container, false)!!

		Picasso.with(context).load(multitap.image).into(view.multitap_dialog_img)

		view.multitap_dialog_name.text = multitap.name

		buttonStar = view.multitap_dialog_button_star
		buttonNotification = view.multitap_dialog_button_notify

		adjustFavoriteButton()
		adjustNotificationButton()

		buttonStar.setOnClickListener {
			multitapController.onStarClicked()
			adjustFavoriteButton()
			adjustNotificationButton()
		}

		buttonNotification.setOnClickListener {
			multitapController.onNotificationClicked()
			adjustNotificationButton()
		}


		multitap.details?.let {
			view.multitap_dialog_address.text = it.address

			if (it.coords[0].isEmpty()) {
				view.multitap_dialog_button_map.visibility = GONE
			}
			else {
				view.multitap_dialog_button_map.setOnClickListener {
					multitapController.onMapClicked()
				}
			}

			if (it.website.isEmpty())
				view.multitap_dialog_button_website.visibility = GONE
			else
			{
				if(it.website.contains("facebook"))
				{
					view.multitap_dialog_button_website.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_facebook, 0,0,0)
					view.multitap_dialog_button_website.text = getString(R.string.dialog_facebook)
				}
				view.multitap_dialog_button_website.setOnClickListener {
					multitapController.goToWebsite()
				}
			}

			if (it.phone.isEmpty())
				view.multitap_dialog_button_call.visibility = GONE
			else
			{
				view.multitap_dialog_button_call.text = getString(R.string.dialog_call, it.phone)
				view.multitap_dialog_button_call.setOnClickListener {
					multitapController.launchPhone()
				}
			}


		} ?: run {
			view.multitap_dialog_button_map.visibility = GONE
			view.multitap_dialog_button_website.visibility = GONE
			view.multitap_dialog_button_call.visibility = GONE
		}

		return view
	}

	private fun adjustFavoriteButton()
	{
		if (StaticProvider.Favorites.isFavorite(multitap))
		{
			buttonStar.setText(R.string.menu_unstar)
			buttonStar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_star_24dp, 0, 0,0)
			buttonNotification.isEnabled = true
		}
		else {
			buttonStar.setText(R.string.menu_star)
			buttonStar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_star_border_24dp, 0, 0,0)
			buttonNotification.isEnabled = false
		}
	}

	private fun adjustNotificationButton()
	{
		if (StaticProvider.NotificationMemory.isNotificationEnabled(multitap))
		{
			buttonNotification.setText(R.string.menu_unfollow)
			buttonNotification.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_notification_on_24dp, 0,0, 0)
		}
		else
		{
			buttonNotification.setText(R.string.menu_follow)
			buttonNotification.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_notification_off_24dp, 0,0, 0)
		}

		if (buttonNotification.isEnabled)
		{
			buttonNotification.alpha = 1f
		}
		else
		{
			buttonNotification.alpha = 0.5f
		}
	}
}