package com.goldenpiedevs.schedule.app.core.dao.timetable

import com.goldenpiedevs.schedule.app.core.ext.currentWeek
import com.goldenpiedevs.schedule.app.core.ext.today

fun DaoLessonModel.getDayDate(): String {
    if (lessonWeek.toInt() - 1 == currentWeek) {
        if (dayNumber.toInt() >= today.dayOfWeek.value) {
            return today.plusDays((dayNumber.toInt() - today.dayOfWeek.value).toLong()).toString()
        } else if (dayNumber.toInt() < today.dayOfWeek.value) {
            return today.plusWeeks(2)
                    .plusDays((dayNumber.toInt() - today.dayOfWeek.value).toLong()).toString()
        }
    } else {
        return today.plusWeeks(1)
                .plusDays((dayNumber.toInt() - today.dayOfWeek.value).toLong()).toString()
    }

    return ""
}