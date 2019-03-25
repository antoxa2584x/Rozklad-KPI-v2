package com.goldenpiedevs.schedule.app.ui.widget

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.timetable.*
import com.goldenpiedevs.schedule.app.core.ext.currentWeek
import com.goldenpiedevs.schedule.app.core.ext.todayNumberInWeek
import com.goldenpiedevs.schedule.app.core.utils.preference.AppPreference
import com.goldenpiedevs.schedule.app.ui.lesson.LessonImplementation
import java.util.*

/**
 * WidgetDataProvider acts as the adapter for the collection view widget,
 * providing RemoteViews to the widget in the getViewAt method.
 */
class WidgetDataProvider(val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private var mCollection: MutableList<DaoLessonModel> = ArrayList()

    override fun onCreate() {
        initData()
    }

    override fun onDataSetChanged() {
        initData()
    }

    override fun onDestroy() {

    }

    override fun getCount(): Int {
        return mCollection.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val model = mCollection[position]

        val view = RemoteViews(context.packageName,
                R.layout.widget_item_view)

        view.setViewVisibility(R.id.widget_lesson_has_note, if (model.haveNote) View.VISIBLE else View.INVISIBLE)

        view.setTextViewText(R.id.widget_lesson_title, model.lessonName)
        view.setTextViewText(R.id.widget_lesson_number, model.lessonNumber)
        view.setTextViewText(R.id.widget_lesson_time, model.getTime())
        view.setTextViewText(R.id.widget_lesson_location, "${model.lessonRoom} ${model.lessonType}")

        view.setOnClickPendingIntent(R.id.widget_item_row_view,
                PendingIntent.getBroadcast(context,
                        0,
                        Intent(context, ScheduleWidgetProvider::class.java).apply {
                            action = ScheduleWidgetProvider.ACTION_OPEN_LESSON
                            putExtra(LessonImplementation.LESSON_ID, model.id)
                        },
                        PendingIntent.FLAG_UPDATE_CURRENT))
        return view
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    private fun initData() {
        mCollection.clear()

        val data = DaoDayModel.getLessons()
                .forGroupWithName(AppPreference.groupName)
                .forWeek(currentWeek + 1)
                .firstOrNull { it.dayNumber == todayNumberInWeek }
                ?.lessons
        data?.let {
            mCollection.addAll(it)
        }
    }
}
