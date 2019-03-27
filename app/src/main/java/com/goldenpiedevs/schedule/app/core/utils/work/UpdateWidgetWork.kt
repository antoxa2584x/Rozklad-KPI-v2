package com.goldenpiedevs.schedule.app.core.utils.work

import com.evernote.android.job.DailyJob
import com.evernote.android.job.JobRequest
import com.goldenpiedevs.schedule.app.ui.widget.ScheduleWidgetProvider
import java.util.concurrent.TimeUnit


class UpdateWidgetWork : DailyJob() {
    override fun onRunDailyJob(params: Params): DailyJobResult {
        ScheduleWidgetProvider.updateWidget(context)

        return DailyJobResult.SUCCESS
    }

    companion object {
        const val TAG = "UpdateWidgetWork"

        fun enqueueWork() {
            DailyJob.schedule(JobRequest.Builder(TAG),
                    TimeUnit.MINUTES.toMillis(0),
                    TimeUnit.MINUTES.toMillis(10))
        }
    }
}