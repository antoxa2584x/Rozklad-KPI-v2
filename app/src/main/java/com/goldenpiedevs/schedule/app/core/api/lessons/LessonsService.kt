package com.goldenpiedevs.schedule.app.core.api.lessons

import com.goldenpiedevs.schedule.app.core.dao.timetable.TimeTableResponse
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LessonsService {
    @GET("groups/{id}/timetable")
    fun getGroupTimeTable(@Path("id") id: Int?): Deferred<Response<TimeTableResponse>>
}