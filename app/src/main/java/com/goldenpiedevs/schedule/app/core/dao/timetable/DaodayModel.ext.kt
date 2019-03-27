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
        this.filter { it.weekNumber == week }.sortedBy { it.dayNumber }.toList()

fun Sequence<DaoDayModel>.forTeacher(teacherId: Int): Sequence<DaoDayModel> =
        this.filter { it.parentTeacherId == teacherId }

fun Sequence<DaoDayModel>.forGroupWithName(groupName: String): Sequence<DaoDayModel> =
        this.filter { it.parentGroup == groupName }