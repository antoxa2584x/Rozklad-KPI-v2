package com.goldenpiedevs.schedule.app.ui.timetable

import android.support.design.widget.AppBarLayout
import android.support.v4.widget.NestedScrollView
import android.view.View
import com.goldenpiedevs.schedule.app.ui.base.BasePresenter

interface TimeTablePresenter : BasePresenter<TimeTableView> {
    fun getData()
    fun onLessonClicked(id: String)
    fun scrollToView(appBarLayout: AppBarLayout, scrollView: NestedScrollView, view: View)
    fun showCurrentDay()
}