package com.goldenpiedevs.schedule.app.core.dao.group

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DaoGroupModel : RealmObject() {
    @PrimaryKey
    @SerializedName("group_id")
    var groupId: Int = 0

    @SerializedName("group_okr")
    var groupOkr: String = ""
    @SerializedName("group_full_name")
    var groupFullName: String = ""
    @SerializedName("group_prefix")
    var groupPrefix: String = ""
    @SerializedName("group_type")
    var groupType: String = ""
    @SerializedName("group_url")
    var groupUrl: String = ""

    override fun toString(): String {
        return groupFullName.toUpperCase()
    }
}