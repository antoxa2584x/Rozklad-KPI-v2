package com.goldenpiedevs.schedule.app.core.dao.timetable

import com.goldenpiedevs.schedule.app.core.ext.currentWeek
import com.goldenpiedevs.schedule.app.core.ext.today
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class DaoDayModel : RealmObject() {
    @SerializedName("day_name")
    var dayName: String = ""
    @SerializedName("day_number")
    var dayNumber: Int = -1
    @SerializedName("lessons")
    var lessons: RealmList<DaoLessonModel> = RealmList()

    fun getDayDate(): String {
        if (lessons.first()!!.lessonWeek - 1 == currentWeek) {
            if (dayNumber >= today.dayOfWeek.value) {
                return today.plusDays((dayNumber - today.dayOfWeek.value).toLong()).toString()
            } else if (dayNumber < today.dayOfWeek.value) {
                return today.plusWeeks(2)
                        .plusDays((dayNumber - today.dayOfWeek.value).toLong()).toString()
            }
        } else {
            return today.plusWeeks(1)
                    .plusDays((dayNumber - today.dayOfWeek.value).toLong()).toString()
        }
        return ""
    }

}