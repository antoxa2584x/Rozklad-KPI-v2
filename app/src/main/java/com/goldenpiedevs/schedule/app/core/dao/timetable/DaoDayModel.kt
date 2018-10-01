package com.goldenpiedevs.schedule.app.core.dao.timetable

import com.goldenpiedevs.schedule.app.core.ext.currentWeek
import com.goldenpiedevs.schedule.app.core.ext.today
import com.google.gson.annotations.SerializedName
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
open class DaoDayModel : RealmObject() {

    @PrimaryKey
    var uuid = UUID.randomUUID().toString()

    var dayNumber = "-1"
    var dayName = ""
    var weekNumber = "-1"
    @SerializedName("lessons")
    var lessons: RealmList<DaoLessonModel> = RealmList()
    var parentGroup = ""
    var parentTeacherId = "-1"

    fun getDayDate(): String {
        lessons.first()?.let {
            if (it.lessonWeek.toInt() - 1 == currentWeek) {
                if (dayNumber.toInt() >= today.dayOfWeek.value) {
                    return today.plusDays((dayNumber.toInt() - today.dayOfWeek.value).toLong()).toString()
                } else if (dayNumber.toInt() < today.dayOfWeek.value) {
                    return today.plusWeeks(2)
                            .plusDays((dayNumber.toInt() - today.dayOfWeek.value).toLong()).toString()
                }
            } else {
                return today.plusWeeks(1)
                        .plusDays((dayNumber.toInt() - today.dayOfWeek.value).toLong()).toString()
            }
        }

        return ""
    }

    companion object {
        fun firstWeek(): List<DaoDayModel> {
            val realm = Realm.getDefaultInstance()
            val lessonModel = realm.copyFromRealm(realm.where(DaoDayModel::class.java)
                    .equalTo("weekNumber", 1.toString()).findAll())

            if (!realm.isClosed)
                realm.close()

            return lessonModel
        }

        fun secondWeek(): List<DaoDayModel> {
            val realm = Realm.getDefaultInstance()
            val lessonModel = realm.copyFromRealm(realm.where(DaoDayModel::class.java)
                    .equalTo("weekNumber", 2.toString()).findAll())

            if (!realm.isClosed)
                realm.close()

            return lessonModel
        }

        fun saveGroupTimeTable(key: ArrayList<DaoLessonModel>, groupName: String) {
            val realm = Realm.getDefaultInstance()

            key.groupBy { it.lessonWeek.toInt() }.forEach { (weekNum, weekLessonsList) ->
                weekLessonsList.groupBy { it.dayNumber.toInt() }.forEach { (dayNum, dayLessonsList) ->
                    val model = realm.where(DaoDayModel::class.java).equalTo("parentGroup", groupName)
                            .equalTo("dayNumber", dayNum.toString())
                            .equalTo("weekNumber", weekNum.toString()).findFirst()
                    model?.let { modelIt ->
                        realm.executeTransaction {
                            modelIt.lessons.deleteAllFromRealm()
                            modelIt.lessons.addAll(dayLessonsList)
                            it.copyToRealmOrUpdate(modelIt)
                        }
                    } ?: run {
                        realm.executeTransaction {
                            it.copyToRealmOrUpdate(DaoDayModel().apply {
                                lessons.addAll(dayLessonsList)
                                parentGroup = groupName
                                weekNumber = weekNum.toString()
                                dayNumber = dayNum.toString()
                                dayName = dayLessonsList.first().dayName
                            })
                        }
                    }
                }
            }
        }

        fun saveTeacherTimeTable(key: ArrayList<DaoLessonModel>, teacherId: Int) {
            val realm = Realm.getDefaultInstance()

            key.groupBy { it.lessonWeek.toInt() }.forEach { (weekNum, weekLessonsList) ->
                weekLessonsList.groupBy { it.dayNumber.toInt() }.forEach { (dayNum, dayLessonsList) ->
                    val model = realm.where(DaoDayModel::class.java).equalTo("parentTeacherId", teacherId.toString())
                            .equalTo("dayNumber", dayNum.toString())
                            .equalTo("weekNumber", weekNum.toString()).findFirst()
                    model?.let { modelIt ->
                        realm.executeTransaction {
                            modelIt.lessons.deleteAllFromRealm()
                            modelIt.lessons.addAll(dayLessonsList)
                            it.copyToRealmOrUpdate(modelIt)
                        }
                    } ?: run {
                        realm.executeTransaction {
                            it.copyToRealmOrUpdate(DaoDayModel().apply {
                                lessons.addAll(dayLessonsList)
                                parentTeacherId = teacherId.toString()
                                weekNumber = weekNum.toString()
                                dayNumber = dayNum.toString()
                                dayName = dayLessonsList.first().dayName
                            })
                        }
                    }
                }
            }
        }

        fun firstWeekForTeacher(teacherID: String): Collection<DaoDayModel> {
            val realm = Realm.getDefaultInstance()
            val lessonModel = realm.copyFromRealm(realm.where(DaoDayModel::class.java)
                    .equalTo("weekNumber", 1.toString())
                    .equalTo("parentTeacherId", teacherID).findAll())

            if (!realm.isClosed)
                realm.close()

            return lessonModel
        }

        fun secondWeekForTeacher(teacherID: String): Collection<DaoDayModel> {
            val realm = Realm.getDefaultInstance()
            val lessonModel = realm.copyFromRealm(realm.where(DaoDayModel::class.java)
                    .equalTo("weekNumber", 2.toString())
                    .equalTo("parentTeacherId", teacherID).findAll())

            if (!realm.isClosed)
                realm.close()

            return lessonModel
        }
    }
}