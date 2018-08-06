package com.goldenpiedevs.schedule.app.core.api.lessons

import com.goldenpiedevs.schedule.app.core.dao.BaseResponseModel
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoWeekModel
import com.goldenpiedevs.schedule.app.core.dao.timetable.TimeTableData
import io.realm.Realm
import io.realm.RealmList
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import retrofit2.Response

class LessonsManager(private val lessonsService: LessonsService) {
    fun loadTimeTable(groupId: Int?): Deferred<Response<BaseResponseModel<TimeTableData>>> = async {
        val response = lessonsService.getGroupTimeTable(groupId).await()

        if (response.isSuccessful) {
            val realm = Realm.getDefaultInstance()

            realm.executeTransaction { transaction ->
                transaction.copyToRealmOrUpdate(listOf(response.body()!!.data!!.weeks!!.firstWeekModel!!,
                        response.body()!!.data!!.weeks!!.secondWeekModel!!)
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

        response
    }
}