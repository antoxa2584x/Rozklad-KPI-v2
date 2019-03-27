package com.goldenpiedevs.schedule.app.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.ext.currentWeek
import com.goldenpiedevs.schedule.app.core.ext.todayName
import com.goldenpiedevs.schedule.app.ui.lesson.LessonActivity
import com.goldenpiedevs.schedule.app.ui.lesson.LessonImplementation


class ScheduleWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        appWidgetIds?.let {
            for (appWidgetId in it) {
                updateAppWidget(context, appWidgetManager, appWidgetId)
            }
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        when {
            intent?.action.equals(ACTION_SCHEDULED_UPDATE)
                    || intent?.action.equals("android.appwidget.action.APPWIDGET_UPDATE") -> context?.let {
                val manager = AppWidgetManager.getInstance(it)
                val ids = manager.getAppWidgetIds(ComponentName(it, ScheduleWidgetProvider::class.java))
                onUpdate(it, manager, ids)
            }
            intent?.action.equals(ACTION_OPEN_LESSON) -> context?.let {
                it.startActivity(Intent(it, LessonActivity::class.java)
                        .apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            putExtra(LessonImplementation.LESSON_ID,
                                    intent!!.getStringExtra(LESSON_ID))
                        })
            }
        }

        super.onReceive(context, intent)
    }

    companion object {
        const val ACTION_SCHEDULED_UPDATE = "com.goldenpiedevs.schedule.app.ui.widget.SCHEDULED_UPDATE"
        const val ACTION_OPEN_LESSON = "com.goldenpiedevs.schedule.app.ui.widget.ACTION_OPEN_LESSON"
        const val LESSON_ID = "com.goldenpiedevs.schedule.app.ui.widget.LESSON_ID"

        fun updateWidget(context: Context) {
            val intent = Intent(context, ScheduleWidgetProvider::class.java)
            intent.action = ACTION_SCHEDULED_UPDATE

            context.sendBroadcast(intent)
        }

        internal fun updateAppWidget(context: Context?, appWidgetManager: AppWidgetManager?,
                                     appWidgetId: Int) {
            context?.let {
                with(RemoteViews(it.packageName, R.layout.schedule_widget)) {
                    val intent = Intent(context, WidgetService::class.java)
                    intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
                    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)

                    val toastIntent = Intent(context, ScheduleWidgetProvider::class.java)
                    toastIntent.action = ScheduleWidgetProvider.ACTION_OPEN_LESSON
                    intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
                    val toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT)

                    setPendingIntentTemplate(R.id.widget_list, toastPendingIntent)
                    setRemoteAdapter(R.id.widget_list, intent)

                    setEmptyView(R.id.widget_list, R.id.widget_list_empty_view)
                    setTextViewText(R.id.widget_day_name,
                            todayName.substring(0, 1).toUpperCase() + todayName.substring(1))
                    setTextViewText(R.id.widget_day_date,
                            "${currentWeek + 1} ${context.getString(R.string.week)}")

                    appWidgetManager?.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list)
                    appWidgetManager?.updateAppWidget(appWidgetId, this)
                }
            }
        }
    }
}