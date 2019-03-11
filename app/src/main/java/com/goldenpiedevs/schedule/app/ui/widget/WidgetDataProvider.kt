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
import com.goldenpiedevs.schedule.app.ui.lesson.LessonActivity
import com.goldenpiedevs.schedule.app.ui.lesson.LessonImplementation
import java.util.*

/**
 * WidgetDataProvider acts as the adapter for the collection view widget,
 * providing RemoteViews to the widget in the getViewAt method.
 */
class WidgetDataProvider(val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private var mCollection: MutableList<DaoLessonModel> = ArrayList()
    private var mContext: Context? = null

    init {
        mContext = context
    }

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
        val view = RemoteViews(context.packageName,
                R.layout.widget_item_view)
        val model = mCollection[position]

        view.setViewVisibility(R.id.currentLesson, if (model.hasNote) View.VISIBLE else View.INVISIBLE)
        view.setTextViewText(R.id.lessonTitle, model.lessonName)
        view.setTextViewText(R.id.lessonNumber, model.lessonNumber)
        view.setTextViewText(R.id.time, model.getTime())
        view.setTextViewText(R.id.location, "${model.lessonRoom} ${model.lessonType}")
        view.setOnClickPendingIntent(R.id.widget_item_row_view,
                PendingIntent.getActivity(context, 0,
                        Intent(context, LessonActivity::class.java)
                                .apply { putExtra(LessonImplementation.LESSON_ID, model.id) },
                        0))
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

    companion object {

        private val TAG = "WidgetDataProvider"
    }

}
