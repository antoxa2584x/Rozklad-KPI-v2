package com.goldenpiedevs.schedule.app.core.dao.timetable

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class DaoWeekModel : RealmObject() {
    var days: RealmList<DayModel>? = null
    var weekNumber: String? = null
}