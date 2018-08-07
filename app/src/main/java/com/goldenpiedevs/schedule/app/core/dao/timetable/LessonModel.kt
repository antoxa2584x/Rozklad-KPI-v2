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
    var lessonId: Int = 0

    @Index
    @SerializedName("lesson_name")
    var lessonName: String = ""

    @SerializedName("teacher_name")
    var teacherName: String = ""
    @SerializedName("rooms")
    var rooms: RealmList<RoomModel> = RealmList()
    @SerializedName("teachers")
    var teachers: RealmList<TeacherModel> = RealmList()
    @SerializedName("lesson_week")
    var lessonWeek: Int = -1
    @SerializedName("lesson_room")
    var lessonRoom: String = ""
    @SerializedName("day_name")
    var dayName: String = ""
    @SerializedName("group_id")
    var groupId: String = ""
    @SerializedName("lesson_type")
    var lessonType: String = ""
    @SerializedName("rate")
    var rate: String = ""
    @SerializedName("lesson_full_name")
    var lessonFullName: String = ""
    @SerializedName("day_number")
    var dayNumber: String = ""

    @SerializedName("time_start")
    var timeStart: String = ""
        get() = field.substring(0, field.length - 3)

    @SerializedName("time_end")
    var timeEnd: String = ""
        get() = field.substring(0, field.length - 3)

    @SerializedName("lesson_number")
    var lessonNumber: String = ""

    var noteModel: NoteModel? = null

    var hasNote: Boolean = noteModel != null

    fun getTime() = "$timeStart-$timeEnd"

    fun getLesson(id: Int): LessonModel {
        val realm = Realm.getDefaultInstance()
        val lessonModel = realm.where(LessonModel::class.java).equalTo("lessonId", id).findFirst()
        if (!realm.isClosed)
            realm.close()
        return lessonModel!!
    }
}