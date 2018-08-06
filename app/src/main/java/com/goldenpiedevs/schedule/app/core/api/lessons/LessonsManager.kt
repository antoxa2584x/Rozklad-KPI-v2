package com.goldenpiedevs.schedule.app.core.api.lessons

import com.goldenpiedevs.schedule.app.core.dao.BaseResponseModel
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoWeekModel
import com.goldenpiedevs.schedule.app.core.dao.timetable.TimeTableData
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import retrofit2.Response

class LessonsManager(private val lessonsService: LessonsService) {
    fun loadTimeTable(groupId: Int?): Deferred<Response<BaseResponseModel<TimeTableData>>> = async {
        val response = lessonsService.getGroupTimeTable(groupId).await()

        if (response.isSuccessful) DaoWeekModel().saveTimetable(response.body()!!)

        response
    }
}