package com.goldenpiedevs.schedule.app.core.dao.timetable

import com.goldenpiedevs.schedule.app.core.dao.note.NoteModel
import com.google.gson.annotations.SerializedName
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class LessonModel : RealmObject() {
    @PrimaryKey
    @SerializedName("lesson_id")
    var lessonId: String? = null

    @Index
    @SerializedName("lesson_name")
    var lessonName: String? = null

    @SerializedName("teacher_name")
    var teacherName: String? = null
    @SerializedName("rooms")
    var rooms: RealmList<RoomModel> = RealmList()
    @SerializedName("teachers")
    var teachers: RealmList<TeacherModel> = RealmList()
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

    @SerializedName("time_start")
    var timeStart: String? = null
        get() = field!!.substring(0, field!!.length - 3)

    @SerializedName("time_end")
    var timeEnd: String? = null
        get() = field!!.substring(0, field!!.length - 3)

    @SerializedName("lesson_number")
    var lessonNumber: String? = null

    var hasNote: Boolean = false
        get() {
            val realm = Realm.getDefaultInstance()
            val note = realm.where(NoteModel::class.java).equalTo("lessonId", lessonId).count() > 0
            if (!realm.isClosed)
                realm.close()
            return note
        }

}