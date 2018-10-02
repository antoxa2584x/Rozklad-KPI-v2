package com.goldenpiedevs.schedule.app.ui.timetable

import android.os.Bundle
import com.goldenpiedevs.schedule.app.ui.base.BasePresenter
import java.util.*

interface TimeTablePresenter : BasePresenter<TimeTableView> {
    fun getData(arguments: Bundle?)
    fun onLessonClicked(id: String)
    fun scrollToDay(dateClicked: Date?)
}