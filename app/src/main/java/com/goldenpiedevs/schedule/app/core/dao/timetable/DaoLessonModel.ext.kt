package com.goldenpiedevs.schedule.app.core.dao.timetable

import com.goldenpiedevs.schedule.app.core.ext.currentWeek
import com.goldenpiedevs.schedule.app.core.ext.today
import org.threeten.bp.format.DateTimeFormatter

val dateFormat = DateTimeFormatter.ofPattern("dd.MM.yy")!!

fun DaoLessonModel.getDayDate(): String {
    if (lessonWeek.toInt() - 1 == currentWeek) {
        if (dayNumber.toInt() >= today.dayOfWeek.value) {
            return today.plusDays((dayNumber.toInt() - today.dayOfWeek.value).toLong()).format(dateFormat)
        } else if (dayNumber.toInt() < today.dayOfWeek.value) {
            return today.plusWeeks(2)
                    .plusDays((dayNumber.toInt() - today.dayOfWeek.value).toLong()).format(dateFormat)
        }
    } else {
        return today.plusWeeks(1)
                .plusDays((dayNumber.toInt() - today.dayOfWeek.value).toLong()).format(dateFormat)
    }

    return ""
}