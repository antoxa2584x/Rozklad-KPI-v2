package com.goldenpiedevs.schedule.app.ui.timetable

import android.support.design.widget.AppBarLayout
import android.support.v4.widget.NestedScrollView
import android.view.View
import com.goldenpiedevs.schedule.app.ui.base.BasePresenter
import java.util.*

interface TimeTablePresenter : BasePresenter<TimeTableView> {
    fun getData()
    fun onLessonClicked(id: Int)
    fun scrollToView(appBarLayout: AppBarLayout, scrollView: NestedScrollView, view: View, forceDisableAnimDelay: Boolean)
    fun showCurrentDay()
    fun scrollToDay(dateClicked: Date?)
}