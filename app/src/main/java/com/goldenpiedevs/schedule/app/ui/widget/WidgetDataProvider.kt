package com.goldenpiedevs.schedule.app.ui.widget

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.content.ContextCompat
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.timetable.*
import com.goldenpiedevs.schedule.app.core.ext.currentWeek
import com.goldenpiedevs.schedule.app.core.ext.todayNumberInWeek
import com.goldenpiedevs.schedule.app.core.utils.preference.AppPreference
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
        mCollection.clear()
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
        view.setTextColor(R.id.widget_lesson_number,
                if (model.haveNote) Color.WHITE else ContextCompat.getColor(context, R.color.secondary_text))
        view.setTextViewText(R.id.widget_lesson_number, model.lessonNumber.toString())
        view.setTextViewText(R.id.widget_lesson_time, model.getTime())
        view.setTextViewText(R.id.widget_lesson_location, "${model.lessonRoom} ${model.lessonType}")

        val extras = Bundle()
        extras.putString(ScheduleWidgetProvider.LESSON_ID, model.id)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        view.setOnClickFillInIntent(R.id.widget_item_row_view, fillInIntent)

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
