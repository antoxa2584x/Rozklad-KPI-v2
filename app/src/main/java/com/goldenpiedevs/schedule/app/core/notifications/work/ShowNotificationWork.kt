package com.goldenpiedevs.schedule.app.core.notifications.work

import com.evernote.android.job.Job
import com.evernote.android.job.JobRequest
import com.evernote.android.job.util.support.PersistableBundleCompat
import com.goldenpiedevs.schedule.app.ScheduleApplication
import com.goldenpiedevs.schedule.app.core.notifications.manger.NotificationManager
import javax.inject.Inject


class ShowNotificationWork : Job() {
    override fun onRunJob(params: Params): Result {
        (context as ScheduleApplication).appComponent.inject(this)

        notificationManager.showNotification(params.extras.getString(LESSON_ID, ""))
        return Result.SUCCESS
    }

    @Inject
    lateinit var notificationManager: NotificationManager


    companion object {
        const val TAG = "ShowNotificationWork"
        private const val LESSON_ID = "lesson_id"

        fun enqueueWork(lessonId: String, timeToNotify: Long): Int {
            val dataBuilder = PersistableBundleCompat().apply {
                putString(LESSON_ID, lessonId)
            }

           return JobRequest.Builder(TAG)
                    .setExact(timeToNotify)
                    .setExtras(dataBuilder)
                    .build()
                    .schedule()
        }
    }
}