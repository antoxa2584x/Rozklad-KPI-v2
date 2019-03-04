package com.goldenpiedevs.schedule.app.core.api.lessons

import com.goldenpiedevs.schedule.app.core.dao.BaseResponseModel
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoLessonModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LessonsService {
    @GET("groups/{id}/lessons")
    fun getGroupTimeTable(@Path("id") id: String?): Deferred<Response<BaseResponseModel<ArrayList<DaoLessonModel>>>>

    @GET("teachers/{id}/lessons")
    fun getTeacherTimeTable(@Path("id") id: Int?): Deferred<Response<BaseResponseModel<ArrayList<DaoLessonModel>>>>
}