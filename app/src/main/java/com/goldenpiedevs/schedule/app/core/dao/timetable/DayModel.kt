package com.goldenpiedevs.schedule.app.core.dao.timetable

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class DayModel : RealmObject() {
    @SerializedName("day_name")
    var dayName: String = ""
    @SerializedName("day_number")
    var dayNumber: Int = -1
    @SerializedName("lessons")
    var lessons: RealmList<LessonModel> = RealmList()
}