package com.goldenpiedevs.schedule.app.core.dao.group

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DaoGroupModel : RealmObject() {
    @PrimaryKey
    @SerializedName("group_id")
    var groupId: Int? = 0

    @SerializedName("group_okr")
    var groupOkr: String? = null
    @SerializedName("group_full_name")
    var groupFullName: String? = null
    @SerializedName("group_prefix")
    var groupPrefix: String? = null
    @SerializedName("group_type")
    var groupType: String? = null
    @SerializedName("group_url")
    var groupUrl: String? = null

    override fun toString(): String {
        return groupFullName!!.toUpperCase()
    }
}