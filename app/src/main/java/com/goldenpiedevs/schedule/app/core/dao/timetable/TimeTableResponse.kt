package com.goldenpiedevs.schedule.app.core.dao.timetable

import com.google.gson.annotations.SerializedName

data class TimeTableResponse(@SerializedName("timeStamp")
                             val timeStamp: Int = 0,
                             @SerializedName("data")
                             val data: DataModel,
                             @SerializedName("message")
                             val message: String = "",
                             @SerializedName("statusCode")
                             val statusCode: Int = 0)