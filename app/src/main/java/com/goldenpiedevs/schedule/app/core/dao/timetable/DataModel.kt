package com.goldenpiedevs.schedule.app.core.dao.timetable

import com.goldenpiedevs.schedule.app.core.dao.group.GroupModel
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class DataModel : RealmObject() {
    @SerializedName("weeks")
    var weeks: WeeksModel? = null
    @SerializedName("group")
    var group: GroupModel? = null
}