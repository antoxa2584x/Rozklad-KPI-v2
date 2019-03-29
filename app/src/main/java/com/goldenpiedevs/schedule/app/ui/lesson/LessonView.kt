package com.goldenpiedevs.schedule.app.ui.lesson

import com.goldenpiedevs.schedule.app.ui.base.BaseView

interface LessonView : BaseView {
    fun showLessonName(string: String)
    fun showLessonTeachers(string: String)
    fun showLessonRoom(string: String)
    fun showLessonType(string: String)
    fun showLessonLocation(roomLatitude: Double, roomLongitude: Double)
    fun showLessonTime(string: String)
}