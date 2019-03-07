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
    var teacherId: String = ""

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

    var hasLoadedSchedule = false

    @LinkingObjects("teachers")
    private val groups: RealmResults<DaoGroupModel>? = null

    companion object {
        fun getTeacher(id: Int): DaoTeacherModel {
            val realm = Realm.getDefaultInstance()
            val teacher = realm.copyFromRealm(
                    realm.where(DaoTeacherModel::class.java).equalTo("teacherId", id.toString()).findFirst()!!)

            if (!realm.isClosed)
                realm.close()

            return teacher
        }
    }


    fun save() {
        val realm = Realm.getDefaultInstance()

        realm.executeTransaction { transaction ->
            transaction.copyToRealmOrUpdate(this@DaoTeacherModel)
        }
        if (!realm.isClosed)
            realm.close()
    }

}