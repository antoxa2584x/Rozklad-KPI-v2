package com.goldenpiedevs.schedule.app.core.dao.group

import com.google.gson.annotations.SerializedName

data class GropuListResponse(@SerializedName("message")
                             val message: String = "",
                             @SerializedName("statusCode")
                             val statusCode: Int = 0,
                             @SerializedName("data")
                             val data: List<GroupModel>?)