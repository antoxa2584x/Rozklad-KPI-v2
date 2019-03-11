package com.goldenpiedevs.schedule.app.core.utils.work

import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator


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