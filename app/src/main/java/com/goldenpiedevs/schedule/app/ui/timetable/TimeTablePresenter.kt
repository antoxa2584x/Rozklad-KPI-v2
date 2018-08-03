package com.goldenpiedevs.schedule.app.ui.timetable

import com.goldenpiedevs.schedule.app.ui.base.BasePresenter

interface TimeTablePresenter : BasePresenter<TimeTableView> {
    fun getData()
    fun onLessonClicked(id: String)
}