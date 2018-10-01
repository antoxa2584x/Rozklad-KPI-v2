package com.goldenpiedevs.schedule.app.core.notifications.work

import android.content.Context
import androidx.work.*
import com.goldenpiedevs.schedule.app.ScheduleApplication
import com.goldenpiedevs.schedule.app.core.notifications.manger.NotificationManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ShowNotificationWork(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
    @Inject
    lateinit var notificationManager: NotificationManager

    override fun doWork(): Result {
        (applicationContext as ScheduleApplication).appComponent.inject(this)

        notificationManager.showNotification(inputData.getString(LESSON_ID)!!)
        return Result.SUCCESS
    }

    companion object {
        private const val TAG = "ShowNotificationWork"
        private const val LESSON_ID = "lesson_id"

        fun enqueueWork(lessonId: String, timeToNotify: Long) {
            val dataBuilder = Data.Builder().apply {
                putString(LESSON_ID, lessonId)
            }

            val workRequest = OneTimeWorkRequest.Builder(ShowNotificationWork::class.java)
                    .setInputData(dataBuilder.build())
                    .setInitialDelay(timeToNotify, TimeUnit.MILLISECONDS)
                    .addTag(TAG)
                    .build()

            WorkManager.getInstance()
                    .beginUniqueWork(lessonId, ExistingWorkPolicy.REPLACE, workRequest)
                    .enqueue()
        }
    }
}