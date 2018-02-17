package me.kosert.ontap.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri


/**
 * Created by Kosert on 2018-02-17.
 */

class ExternalUtil
{
	companion object
	{
		fun launchWebsite(context: Context, url: String)
		{
			val intent = Intent(Intent.ACTION_VIEW)

			val targetUrl = if (url.contains("facebook"))
			{
				val pageName = url.split("/").last()
				val packageManager = context.packageManager

				try
				{
					val versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode
					if (versionCode >= 3002850)
					{
						"fb://facewebmodal/f?href=" + url
					}
					else
					{
						"fb://page/" + pageName
					}
				} catch (e: PackageManager.NameNotFoundException)
				{
					url
				}
			}
			else url

			intent.data = Uri.parse(targetUrl)
			context.startActivity(intent)
		}

		fun launchDialer(context: Context, phone: String)
		{
			val intent = Intent(Intent.ACTION_DIAL, Uri.parse(phone))
			context.startActivity(intent)
		}

		fun launchNavigation()
		{
			//TODO Google Maps directions
		}
	}
}