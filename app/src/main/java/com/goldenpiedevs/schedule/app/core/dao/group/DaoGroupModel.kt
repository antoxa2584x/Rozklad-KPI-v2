package com.goldenpiedevs.schedule.app.core.dao.group

import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoTeacherModel
import com.google.gson.annotations.SerializedName
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmList
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

    var teachers: RealmList<DaoTeacherModel>? = null

    override fun toString(): String {
        return groupFullName.toUpperCase()
    }

    companion object {
        fun getAllTeachersForGroup(groupId: Int): OrderedRealmCollection<DaoTeacherModel> {
            return Realm.getDefaultInstance().where(DaoTeacherModel::class.java).equalTo("groups.groupId", groupId).findAll()
        }

        fun getGroup(groupId: String): DaoGroupModel? {
            val realm = Realm.getDefaultInstance()
            val groupDao = realm.where(DaoGroupModel::class.java).equalTo("groupId", groupId.toInt()).findFirst()

            val group = groupDao?.let { realm.copyFromRealm(it) }

            if (!realm.isClosed)
                realm.close()

            return group
        }
    }

    fun save() {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            it.copyToRealmOrUpdate(this)
        }

        if (!realm.isClosed)
            realm.close()
    }
}