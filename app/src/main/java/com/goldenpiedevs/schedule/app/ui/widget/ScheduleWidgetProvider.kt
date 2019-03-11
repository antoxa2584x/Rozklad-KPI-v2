package com.goldenpiedevs.schedule.app.ui.widget

import android.annotation.TargetApi
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.RemoteViews
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.timetable.*
import com.goldenpiedevs.schedule.app.core.ext.currentWeek
import com.goldenpiedevs.schedule.app.core.ext.today
import com.goldenpiedevs.schedule.app.core.ext.todayName
import com.goldenpiedevs.schedule.app.core.ext.todayNumberInWeek
import com.goldenpiedevs.schedule.app.core.utils.AppPreference


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
        if (intent?.action.equals(ACTION_SCHEDULED_UPDATE)) {
            context?.let {
                val manager = AppWidgetManager.getInstance(it)
                val ids = manager.getAppWidgetIds(ComponentName(it, ScheduleWidgetProvider::class.java))
                onUpdate(it, manager, ids)
            }
        }

        super.onReceive(context, intent)
    }

    companion object {
        const val ACTION_SCHEDULED_UPDATE = "com.goldenpiedevs.schedule.app.ui.widget.SCHEDULED_UPDATE"

        fun updateWidget(context: Context) {
            val intent = Intent(context, ScheduleWidgetProvider::class.java)
            intent.action = ACTION_SCHEDULED_UPDATE

            context.sendBroadcast(intent)
        }

        internal fun updateAppWidget(context: Context?, appWidgetManager: AppWidgetManager?,
                                     appWidgetId: Int) {
            context?.let {
                with(RemoteViews(it.packageName, R.layout.collection_widget)) {
                    setRemoteAdapter(it, this)
                    appWidgetManager?.updateAppWidget(appWidgetId, this)
                }
            }
        }

        /**
         * Sets the remote adapter used to fill in the list items
         *
         * @param views RemoteViews to set the RemoteAdapter
         */
        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        private fun setRemoteAdapter(context: Context, views: RemoteViews) {
            views.setRemoteAdapter(R.id.widget_list,
                    Intent(context, WidgetService::class.java))

            if (!DaoDayModel.getLessons()
                            .forGroupWithName(AppPreference.groupName)
                            .forWeek(currentWeek + 1)
                            .any { it.dayNumber == todayNumberInWeek }) {
                views.setViewVisibility(R.id.widget_list, View.GONE)
                views.setViewVisibility(R.id.widget_list_empty_view, View.VISIBLE)
            }

            views.setTextViewText(R.id.widget_day_name, todayName)
            views.setTextViewText(R.id.widget_day_date, today.format(dateFormat))
        }
    }
}