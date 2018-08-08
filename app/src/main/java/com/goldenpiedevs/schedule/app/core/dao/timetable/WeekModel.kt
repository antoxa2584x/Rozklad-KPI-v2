package com.goldenpiedevs.schedule.app.core.dao.timetable

import com.google.gson.annotations.SerializedName

data class WeekModel(
        @SerializedName("week_number")
        val weekNumber: String? = null,
        @SerializedName("days")
        val daysMap: HashMap<String, DaoDayModel>? = null)