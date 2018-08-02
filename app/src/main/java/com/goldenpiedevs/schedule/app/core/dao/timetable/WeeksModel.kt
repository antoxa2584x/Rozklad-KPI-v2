package com.goldenpiedevs.schedule.app.core.dao.timetable

import com.google.gson.annotations.SerializedName

open class WeeksModel(
        @SerializedName("1")
        val firstWeekModel: WeekModel? = null,
        @SerializedName("2")
        val secondWeekModel: WeekModel? = null
)