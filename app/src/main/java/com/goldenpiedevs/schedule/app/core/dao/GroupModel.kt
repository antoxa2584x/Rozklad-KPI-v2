package com.goldenpiedevs.schedule.app.core.dao

import com.google.gson.annotations.SerializedName

data class GroupModel(@SerializedName("group_id")
                      val groupId: Int = 0,
                      @SerializedName("group_okr")
                      val groupOkr: String = "",
                      @SerializedName("group_full_name")
                      val groupFullName: String = "",
                      @SerializedName("group_prefix")
                      val groupPrefix: String = "",
                      @SerializedName("group_type")
                      val groupType: String = "",
                      @SerializedName("group_url")
                      val groupUrl: String = "") {
    override fun toString(): String {
        return groupFullName.toUpperCase()
    }
}