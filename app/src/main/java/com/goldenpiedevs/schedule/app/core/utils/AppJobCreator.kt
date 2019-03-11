package com.goldenpiedevs.schedule.app.core.utils

import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator
import com.goldenpiedevs.schedule.app.core.notifications.work.ShowNotificationWork
import com.goldenpiedevs.schedule.app.core.notifications.work.UpdateWidgetWork


/**
 * Created by Anton. A on 12.10.2018.
 * Version 1.0
 */
class AppJobCreator:JobCreator {
    override fun create(tag: String): Job? {
        return when (tag) {
            ShowNotificationWork.TAG -> ShowNotificationWork()
            UpdateWidgetWork.TAG -> UpdateWidgetWork()
            else -> null
        }
    }
}