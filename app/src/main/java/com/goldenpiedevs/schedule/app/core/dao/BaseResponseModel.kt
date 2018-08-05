package com.goldenpiedevs.schedule.app.core.dao

import com.google.gson.annotations.SerializedName

open class BaseResponseModel<T : Any> {
    @SerializedName("timeStamp")
    var timeStamp: Int = 0
    @SerializedName("data")
    open var data: T? = null
    @SerializedName("meta")
    var meta: String = ""
    @SerializedName("debugInfo")
    var debugInfo: String = ""
    @SerializedName("message")
    var message: String = ""
    @SerializedName("statusCode")
    var statusCode: Int = 0
}