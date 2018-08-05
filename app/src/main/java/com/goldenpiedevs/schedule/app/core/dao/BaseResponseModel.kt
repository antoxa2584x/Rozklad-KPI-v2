package com.goldenpiedevs.schedule.app.core.api

import com.google.gson.annotations.SerializedName

data class BaseResponseModel(@SerializedName("timeStamp")
                             val timeStamp: Int = 0,
                             @SerializedName("data")
                             val data: Data,
                             @SerializedName("meta")
                             val meta: Null = null,
                             @SerializedName("debugInfo")
                             val debugInfo: Null = null,
                             @SerializedName("message")
                             val message: String = "",
                             @SerializedName("statusCode")
                             val statusCode: Int = 0)