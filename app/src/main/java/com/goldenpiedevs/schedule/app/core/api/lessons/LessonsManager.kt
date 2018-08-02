package com.goldenpiedevs.schedule.app.core.api.lessons

import android.content.Context
import com.goldenpiedevs.schedule.app.core.dao.timetable.TimeTableResponse
import com.vicpin.krealmextensions.save
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import retrofit2.Response

class LessonsManager(val lessonsService: LessonsService, val context: Context) {
    fun loadTimeTable(groupId: Int?): Deferred<Response<TimeTableResponse>> = async {
        val response = lessonsService.getGroupTimeTable(groupId).await()
        if (response.isSuccessful)
            response.body()!!.data.save()

        response
    }
}