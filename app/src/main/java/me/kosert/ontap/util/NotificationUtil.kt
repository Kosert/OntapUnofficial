package me.kosert.ontap.util

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import me.kosert.ontap.background.NotificationService


/**
 * Created by Kosert on 2018-02-18.
 */
class NotificationUtil
{
	companion object
	{
		private const val JOB_ID = 2137

		fun scheduleJob(context: Context)
		{
			val serviceComponent = ComponentName(context, NotificationService::class.java)
			val builder = JobInfo.Builder(JOB_ID, serviceComponent)
			builder.setMinimumLatency(5* 60 * 1000)
			builder.setOverrideDeadline(10* 60 * 1000)
			builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
			val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
			jobScheduler.schedule(builder.build())
		}

	}
}