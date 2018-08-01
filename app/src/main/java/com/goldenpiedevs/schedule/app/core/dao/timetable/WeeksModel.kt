package com.goldenpiedevs.schedule.app.core.dao.timetable

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class WeeksModel : RealmObject() {
    @SerializedName("1")
    var firstWeekModel: WeekModel? = null
    @SerializedName("2")
    var secondWeekModel: WeekModel? = null
}