package com.goldenpiedevs.schedule.app.core.notifications.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class GenerateNotificationsForNextDay (context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
    override fun doWork(): Result {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}