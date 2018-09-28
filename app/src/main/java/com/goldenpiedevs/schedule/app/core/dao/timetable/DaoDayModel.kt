package com.goldenpiedevs.schedule.app.core.dao.timetable

import com.goldenpiedevs.schedule.app.core.ext.currentWeek
import com.goldenpiedevs.schedule.app.core.ext.today
import com.google.gson.annotations.SerializedName
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

open class DaoDayModel{

    @SerializedName("day_name")
    var dayName: String = ""
    @SerializedName("day_number")
    var dayNumber: Int = -1
    @SerializedName("lessons")
    var lessons: List<DaoLessonModel> = ArrayList()

    fun getDayDate(): String {
        if (lessons.first().lessonWeek.toInt() - 1 == currentWeek) {
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

    companion object {
        fun firstWeek(): List<DaoDayModel> {
            val realm = Realm.getDefaultInstance()
            val lessonModel = realm.copyFromRealm(realm.where(DaoLessonModel::class.java)
                    .equalTo("lessonWeek", 1.toString()).findAll())

            if (!realm.isClosed)
                realm.close()

            lessonModel.groupBy {  }
        }

        fun secondWeek(): List<DaoDayModel> {
            val realm = Realm.getDefaultInstance()
            val lessonModel = realm.copyFromRealm(realm.where(DaoLessonModel::class.java)
                    .equalTo("lessonWeek", 1.toString()).findAll())

            if (!realm.isClosed)
                realm.close()

            return lessonModel.onEach { day ->
                val lessons = day.lessons.filter { it.dayNumber.toInt() == day.dayNumber }

                day.lessons.apply {
                    clear()
                    addAll(lessons)
                }
            }
        }
    }

}