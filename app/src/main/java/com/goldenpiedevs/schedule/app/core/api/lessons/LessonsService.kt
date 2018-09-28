package com.goldenpiedevs.schedule.app.core.api.lessons

import com.goldenpiedevs.schedule.app.core.dao.BaseResponseModel
import com.goldenpiedevs.schedule.app.core.dao.timetable.TimeTableData
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LessonsService {
    @GET("groups/{id}/timetable")
    fun getGroupTimeTable(@Path("id") id: Int?): Deferred<Response<BaseResponseModel<TimeTableData>>>

    @GET("teachers/{id}/lessons")
    fun getTeacherTimeTable(@Path("id") id: Int?): Deferred<Response<BaseResponseModel<TimeTableData>>>
}