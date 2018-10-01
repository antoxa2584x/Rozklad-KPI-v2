package com.goldenpiedevs.schedule.app.core.dao.timetable

import com.goldenpiedevs.schedule.app.core.dao.group.DaoGroupModel
import com.goldenpiedevs.schedule.app.core.dao.note.DaoNoteModel
import com.google.gson.annotations.SerializedName
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
open class DaoLessonModel : RealmObject() {

    @PrimaryKey
    var id = UUID.randomUUID().toString()

    @SerializedName("lesson_id")
    var lessonId: Int = 0

    @Index
    @SerializedName("lesson_name")
    var lessonName: String = ""

    @SerializedName("teacher_name")
    var teacherName: String = ""
    @SerializedName("rooms")
    var rooms: RealmList<DaoRoomModel> = RealmList()
    @SerializedName("teachers")
    var teachers: RealmList<DaoTeacherModel> = RealmList()
    @SerializedName("groups")
    var groups: RealmList<DaoGroupModel> = RealmList()
    @SerializedName("lesson_week")
    var lessonWeek: String = ""
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

    var noteModel: DaoNoteModel? = null

    var hasNote: Boolean = noteModel != null

    var showNotification = true

    fun getTime() = "$timeStart-$timeEnd"

    companion object {
        fun getLesson(lessonId: String): DaoLessonModel {
            val realm = Realm.getDefaultInstance()
            val lessonModel = realm.copyFromRealm(realm.where(DaoLessonModel::class.java).equalTo("id", lessonId).findFirst()!!)
            if (!realm.isClosed)
                realm.close()
            return lessonModel!!
        }
    }
}