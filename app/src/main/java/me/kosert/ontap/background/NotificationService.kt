package me.kosert.ontap.background

import android.app.job.JobParameters
import android.app.job.JobService

/**
 * Created by Kosert on 2018-02-18.
 */
class NotificationService : JobService()
{
	override fun onStartJob(params: JobParameters?): Boolean
	{

		//TODO



		return true
	}

	override fun onStopJob(params: JobParameters?): Boolean
	{
		return true
	}

}