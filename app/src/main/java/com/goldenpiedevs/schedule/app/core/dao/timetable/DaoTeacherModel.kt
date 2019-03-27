package com.goldenpiedevs.schedule.app.core.dao.timetable

import com.goldenpiedevs.schedule.app.core.dao.group.DaoGroupModel
import com.google.gson.annotations.SerializedName
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass


@RealmClass
open class DaoTeacherModel : RealmObject() {
    @PrimaryKey
    @SerializedName("teacher_id")
    var teacherId: Int = -1

    @SerializedName("teacher_full_name")
    var teacherFullName: String = ""
    @SerializedName("teacher_name")
    var teacherName: String = ""
        get() {
            return if (Character.isLowerCase(field.toCharArray()[0])) {
                field.substringAfter(" ")
            } else
                field
        }

    @SerializedName("teacher_url")
    var teacherUrl: String = ""
    @SerializedName("teacher_rating")
    var teacherRating: Float = 0.0f
    @SerializedName("teacher_short_name")
    var teacherShortName: String = ""

    var hasLoadedSchedule: Boolean = false

    @LinkingObjects("teachers")
    private val groups: RealmResults<DaoGroupModel>? = null

    companion object {
        fun getTeacher(id: Int): DaoTeacherModel? {
            val realm = Realm.getDefaultInstance()

            var teacher: DaoTeacherModel? = null

            getManagedTeacher(id, realm)?.let {
                teacher = realm.copyFromRealm(it)
            }

            if (!realm.isClosed)
                realm.close()

            return teacher
        }

        fun getManagedTeacher(id: Int, realm: Realm): DaoTeacherModel? {
            return realm.where(DaoTeacherModel::class.java).equalTo("teacherId", id).findFirst()
        }

    }

    fun save() {
        val realm = Realm.getDefaultInstance()

        realm.executeTransaction {
            it.copyToRealmOrUpdate(this)
        }

        if (!realm.isClosed)
            realm.close()
    }

}