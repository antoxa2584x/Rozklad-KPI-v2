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

    private fun getWeekDays(weekNumber: Int): ArrayList<DaoDayModel> {
        val realm = Realm.getDefaultInstance()
        val data = realm.copyFromRealm(realm.where(DaoWeekModel::class.java)
                .equalTo("weekNumber", weekNumber.toString()).findFirst()!!.days) as ArrayList<DaoDayModel>
        if (!realm.isClosed)
            realm.close()
        return data
    }

    fun getFirstWeekDays(): ArrayList<DaoDayModel> {
        return getWeekDays(1)
    }

    fun getSecondWeekDays(): ArrayList<DaoDayModel> {
        return getWeekDays(2)
    }
}