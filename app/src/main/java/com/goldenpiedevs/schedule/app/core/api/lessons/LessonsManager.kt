package com.goldenpiedevs.schedule.app.core.api.lessons

import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoWeekModel
import com.goldenpiedevs.schedule.app.core.dao.timetable.TimeTableResponse
import com.vicpin.krealmextensions.saveAll
import io.realm.RealmList
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import retrofit2.Response

class LessonsManager(private val lessonsService: LessonsService) {
    fun loadTimeTable(groupId: Int?): Deferred<Response<TimeTableResponse>> = async {
        val response = lessonsService.getGroupTimeTable(groupId).await()

        if (response.isSuccessful) {
            listOf(response.body()!!.data.weeks!!.firstWeekModel!!,
                    response.body()!!.data.weeks!!.secondWeekModel!!)
                    .map {
                        val daoWeekModel = DaoWeekModel()
                        daoWeekModel.weekNumber = it.weekNumber

                        daoWeekModel.days = RealmList()
                        daoWeekModel.days!!.addAll(it.daysMap!!)

                        daoWeekModel
                    }.saveAll()
        }

        response
    }
}