package me.kosert.ontap.background

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import me.kosert.ontap.R
import me.kosert.ontap.data.StaticProvider
import me.kosert.ontap.model.BeerState
import me.kosert.ontap.model.Multitap
import me.kosert.ontap.ui.activities.multitap.MultitapActivity


/**
 * Created by Kosert on 2018-02-18.
 */
class NotificationUtil
{
	companion object
	{
		private fun initChannel(context: Context)
		{
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
			{
				return
			}
			val channelId = context.getString(R.string.notification_channel_id)
			val channelName = context.getString(R.string.notification_channel_name)
			val channelDesc = context.getString(R.string.notification_channel_description)

			val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
			val channel = NotificationChannel(channelId,
					channelName,
					NotificationManager.IMPORTANCE_DEFAULT)
			channel.description = channelDesc
			notificationManager.createNotificationChannel(channel)
		}

		private const val NOTIFICATION_DISMISSED = "me.kosert.ontap.NOTIFICATION_DISMISSED"

		private fun createNotification(context: Context, multitap: Multitap, newBeers: List<BeerState>): Notification
		{
			val channelId = context.getString(R.string.notification_channel_id)

			val newBeersCount = newBeers.size
			val title = context.resources.getQuantityString(R.plurals.notification_title, newBeersCount, newBeersCount, multitap.name)

			val first = newBeers.first()
			val text = if (newBeersCount == 1)
				context.getString(R.string.notification_text_one, first.name, first.brewery)
			else
				context.resources.getQuantityString(R.plurals.notification_text_more, newBeersCount - 1, first.name, first.brewery, newBeersCount - 1)

			val builder = NotificationCompat.Builder(context, channelId)
					.setSmallIcon(R.drawable.ic_launcher_white)
					.setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
					.setColor(ContextCompat.getColor(context, R.color.colorPrimary))
					.setContentTitle(title)
					.setTicker(title)
					.setContentText(text)
					.setAutoCancel(true)


			val requestId = StaticProvider.Favorites.getPosition(multitap)
			val requestId2 = requestId + 10000

			val deleteIntent = Intent(context, NotificationDismissReceiver::class.java)
			deleteIntent.action = NOTIFICATION_DISMISSED
			val json = StaticProvider.getGson().toJson(multitap)
			deleteIntent.putExtra(MULTITAP_JSON_EXTRA, json)
			val deletePendingIntent = PendingIntent.getBroadcast(context, requestId, deleteIntent, 0)

			builder.setDeleteIntent(deletePendingIntent)

			val intent = Intent(context, MultitapActivity::class.java)
			val multitapJson = StaticProvider.getGson().toJson(multitap)

			intent.putExtra(MultitapActivity.EXTRA_MULTITAP, multitapJson)
			val pendingIntent = PendingIntent.getActivity(context, requestId2, intent, PendingIntent.FLAG_UPDATE_CURRENT)

			builder.setContentIntent(pendingIntent)

			if (StaticProvider.Prefs.getPrefBoolean(StaticProvider.Prefs.PrefType.SOUND_KEY))
			{
				val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
				builder.setSound(uri)
			}
			if (StaticProvider.Prefs.getPrefBoolean(StaticProvider.Prefs.PrefType.VIBRATE_KEY))
			{
				builder.setDefaults(Notification.DEFAULT_VIBRATE)
			}
			if (StaticProvider.Prefs.getPrefBoolean(StaticProvider.Prefs.PrefType.LED_KEY))
			{
				builder.setLights(Color.YELLOW, 2000, 1000)
			}

			return builder.build()
		}

		fun showNotification(context: Context, multitap: Multitap, newBeers: List<BeerState>)
		{
			if (StaticProvider.isPrefsNotInitialized())
			{
				val key = context.getString(R.string.preference_key)
				StaticProvider.initializePrefs(context.getSharedPreferences(key, Context.MODE_PRIVATE))
			}

			initChannel(context)
			val notification = createNotification(context, multitap, newBeers)

			val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

			val requestId = StaticProvider.Favorites.getPosition(multitap)
			notificationManager.notify(requestId, notification)
		}
	}
}