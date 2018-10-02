package com.goldenpiedevs.schedule.app.core.dao.timetable

import io.realm.Realm

fun DaoDayModel.Companion.getLessons(): Sequence<DaoDayModel> {
    val realm = Realm.getDefaultInstance()
    val lessonModel = realm.copyFromRealm(realm.where(DaoDayModel::class.java).findAll())

    if (!realm.isClosed)
        realm.close()

    return lessonModel.asSequence()
}

fun Sequence<DaoDayModel>.forWeek(week: Int): List<DaoDayModel> =
        this.toList().filter { it.weekNumber == week.toString() }

fun List<DaoDayModel>.forTeacher(teacherId: String?): List<DaoDayModel> =
        this.filter { it.parentTeacherId == teacherId.toString() }

fun List<DaoDayModel>.forGroupWithName(groupName: String): List<DaoDayModel> =
        this.filter { it.parentGroup == groupName }