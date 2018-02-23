package me.kosert.ontap.background

import android.app.job.JobParameters
import android.app.job.JobService
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
	var counter : Int = 0

	override fun onStartJob(params: JobParameters): Boolean
	{
		Logger.d("JOB STARTED")

		counter = StaticProvider.NotificationMemory.getNotificationList().size

		StaticProvider.NotificationMemory.getNotificationList().forEach {

			WebRetriever.downloadMultitapBeerStates(it, object : BeerStatesCallback
			{
				override fun onFailure() {
					canFinishJob(params)
				}
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
						Logger.d(it.name + " - NEW BEERS, CREATING NOTIFICATION")
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
						Logger.d(it.name + " - NO NEW BEERS")
					}
					canFinishJob(params)
				}
			})

		}

		BackgroundUtil.scheduleJob(this@NotificationService)
		return true
	}

	private fun canFinishJob(params: JobParameters)
	{
		counter--
		if (counter == 0)
			jobFinished(params, false)
	}

	override fun onStopJob(params: JobParameters?): Boolean
	{
		return true
	}

}