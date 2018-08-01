package com.goldenpiedevs.schedule.app.core.dao.timetable

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class TeacherModel : RealmObject() {
    @SerializedName("teacher_full_name")
    var teacherFullName: String? = null
    @SerializedName("teacher_name")
    var teacherName: String? = null
    @SerializedName("teacher_url")
    var teacherUrl: String? = null
    @SerializedName("teacher_id")
    var teacherId: String? = null
    @SerializedName("teacher_rating")
    var teacherRating: String? = null
    @SerializedName("teacher_short_name")
    var teacherShortName: String? = null
}