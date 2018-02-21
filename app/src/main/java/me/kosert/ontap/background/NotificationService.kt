package me.kosert.ontap.background

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import me.kosert.ontap.R
import me.kosert.ontap.data.StaticProvider
import me.kosert.ontap.data.WebRetriever
import me.kosert.ontap.data.callbacks.BeerStatesCallback
import me.kosert.ontap.model.BeerState
import me.kosert.ontap.util.BackgroundUtil
import me.kosert.ontap.util.Logger

/**
 * Created by Kosert on 2018-02-18.
 */
class NotificationService : JobService()
{
	override fun onStartJob(params: JobParameters?): Boolean
	{
		Logger.d("JOB STARTED")
		if (StaticProvider.isFavoritesNotInitialized())
		{
			Logger.d("FAVS NOT INITIALIZED!")
			val favs = getSharedPreferences(getString(R.string.favorites_key), Context.MODE_PRIVATE)
			StaticProvider.initializeFavorites(favs)
		}

		StaticProvider.NotificationMemory.getNotificationList().forEach {

			WebRetriever.downloadMultitapBeerStates(it, object : BeerStatesCallback
			{
				override fun onFailure() {}
				override fun onSuccess(list: List<BeerState>)
				{
					val newBeers = mutableListOf<BeerState>()
					val prevList = StaticProvider.NotificationMemory.getLastMultitapState(it)
					if (list.size == prevList.size)
					{
						list.forEachIndexed { index, beerState ->
							if (!beerState.equals(prevList[index]))
							{
								newBeers.add(beerState)
							}
						}
					}
					else
						StaticProvider.NotificationMemory.saveLastMultitapState(it, list)

					if (newBeers.size > 0)
					{
						Logger.d("NEW BEERS!!! CREATING NOTIFICATION " + it.name)
						val unread = StaticProvider.NotificationMemory.getNotReadCount(it)

						if (unread > 0)
						{
							repeat(StaticProvider.NotificationMemory.getNotReadCount(it), {
								newBeers.add(BeerState("fake", "fake"))
							})
							NotificationUtil.showNotification(this@NotificationService, it, newBeers)
							StaticProvider.NotificationMemory.setNotReadCount(it, newBeers.size)
						}
						else
						{
							NotificationUtil.showNotification(this@NotificationService, it, newBeers)
							StaticProvider.NotificationMemory.setNotReadCount(it, newBeers.size)
						}
						StaticProvider.NotificationMemory.saveLastMultitapState(it, list)
					}
					else
					{
						Logger.d("NO NEW BEERS")
					}
				}
			})

		}

		BackgroundUtil.scheduleJob(this@NotificationService)
		return true
	}

	override fun onStopJob(params: JobParameters?): Boolean
	{
		return true
	}

}