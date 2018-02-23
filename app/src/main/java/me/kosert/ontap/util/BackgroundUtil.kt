package me.kosert.ontap.util

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import me.kosert.ontap.background.NotificationService
import me.kosert.ontap.data.StaticProvider


/**
 * Created by Kosert on 2018-02-18.
 */
class BackgroundUtil
{
	companion object
	{
		private const val JOB_ID = 2137

		fun checkJob(context: Context)
		{
			val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
			val list = jobScheduler.allPendingJobs
			if (list.isEmpty() && StaticProvider.Prefs.getPrefBoolean(StaticProvider.Prefs.PrefType.NOTIFICATIONS_KEY))
			{
				Logger.d("JOB LIST EMPTY")
				scheduleJob(context)
			}
		}

		fun scheduleJob(context: Context)
		{
			val serviceComponent = ComponentName(context, NotificationService::class.java)
			val builder = JobInfo.Builder(JOB_ID, serviceComponent)

			Logger.d("JOB SCHEDULED")
			val sync = StaticProvider.Prefs.getPrefSyncPeriod()
			builder.setMinimumLatency((sync.minTime * 60 * 1000).toLong())
			builder.setOverrideDeadline((sync.maxTime * 60 * 1000).toLong())
			builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
			val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
			jobScheduler.schedule(builder.build())
		}

		fun disableJob(context: Context)
		{
			val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
			jobScheduler.cancel(JOB_ID)
			Logger.d("JOB DISABLED")
		}
	}
}