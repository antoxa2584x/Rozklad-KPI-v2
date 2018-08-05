package com.goldenpiedevs.schedule.app.core.dao.timetable

import com.goldenpiedevs.schedule.app.core.dao.group.GroupModel
import com.google.gson.annotations.SerializedName

data class TimeTableData(
        @SerializedName("weeks")
        val weeks: WeeksModel? = null,
        @SerializedName("group")
        val group: GroupModel? = null
)