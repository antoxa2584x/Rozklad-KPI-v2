package com.goldenpiedevs.schedule.app.core.dao.timetable

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class LessonModel : RealmObject() {
    @SerializedName("teacher_name")
    var teacherName: String? = null
    @SerializedName("rooms")
    var rooms: RealmList<RoomModel>? = RealmList()
    @SerializedName("lesson_name")
    var lessonName: String? = null
    @SerializedName("time_start")
    var timeStart: String? = null
    @SerializedName("lesson_id")
    var lessonId: String? = null
    @SerializedName("lesson_week")
    var lessonWeek: String? = null
    @SerializedName("lesson_room")
    var lessonRoom: String? = null
    @SerializedName("day_name")
    var dayName: String? = null
    @SerializedName("group_id")
    var groupId: String? = null
    @SerializedName("lesson_type")
    var lessonType: String? = null
    @SerializedName("rate")
    var rate: String? = null
    @SerializedName("lesson_full_name")
    var lessonFullName: String? = null
    @SerializedName("day_number")
    var dayNumber: String? = null
    @SerializedName("time_end")
    var timeEnd: String? = null
    @SerializedName("lesson_number")
    var lessonNumber: String? = null
}