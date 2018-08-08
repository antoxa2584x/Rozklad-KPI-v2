package com.goldenpiedevs.schedule.app.core.dao.timetable

import com.goldenpiedevs.schedule.app.core.dao.BaseResponseModel
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class DaoWeekModel : RealmObject() {
    var days: RealmList<DaoDayModel> = RealmList()
    var weekNumber: String? = null

    fun saveTimetable(body: BaseResponseModel<TimeTableData>) {
        val realm = Realm.getDefaultInstance()

        realm.executeTransaction { transaction ->
            transaction.copyToRealmOrUpdate(listOf(body.data!!.weeks!!.firstWeekModel!!,
                    body.data!!.weeks!!.secondWeekModel!!)
                    .map { map ->
                        val daoWeekModel = DaoWeekModel()
                        daoWeekModel.weekNumber = map.weekNumber

                        daoWeekModel.days = RealmList()
                        daoWeekModel.days.addAll(map.daysMap!!.entries
                                .filter { !it.value.lessons.isEmpty() }
                                .map { it.value })

                        daoWeekModel
                    })
        }

        if (!realm.isClosed)
            realm.close()
    }

    private fun getWeekDays(realm: Realm, weekNumber: Int): RealmList<DaoDayModel> {
        return realm.where(DaoWeekModel::class.java)
                .equalTo("weekNumber", weekNumber.toString()).findFirst()!!.days
    }

    fun getFirstWeekDays(realm: Realm): RealmList<DaoDayModel> {
        return getWeekDays(realm, 1)
    }

    fun getSecondWeekDays(realm: Realm): RealmList<DaoDayModel> {
        return getWeekDays(realm, 2)
    }
}