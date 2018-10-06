package com.goldenpiedevs.schedule.app.ui.timetable

import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoDayModel
import com.goldenpiedevs.schedule.app.ui.base.BaseView

interface TimeTableView : BaseView {
    fun showWeekData(data: MutableList<DaoDayModel>)
    fun showDay(currentDay: Int)
    fun clearTimetable()
}