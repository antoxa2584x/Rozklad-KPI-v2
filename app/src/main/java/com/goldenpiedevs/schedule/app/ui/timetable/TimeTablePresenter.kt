package com.goldenpiedevs.schedule.app.ui.timetable

import android.os.Bundle
import com.goldenpiedevs.schedule.app.ui.base.BasePresenter
import java.util.*

interface TimeTablePresenter : BasePresenter<TimeTableView> {
    fun getData(arguments: Bundle?)
    fun onLessonClicked(id: Int)
    fun showCurrentDay()
    fun scrollToDay(dateClicked: Date?)
}