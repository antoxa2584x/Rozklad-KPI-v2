package com.goldenpiedevs.schedule.app.core.dao.timetable

import com.goldenpiedevs.schedule.app.core.dao.utils.DayModelMapEntity
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class WeekModel : RealmObject() {
    @SerializedName("week_number")
    var weekNumber: String? = null
    @SerializedName("days")
    var daysMap: RealmList<DayModelMapEntity>? = RealmList()
}