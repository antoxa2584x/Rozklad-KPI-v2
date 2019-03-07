package com.goldenpiedevs.schedule.app.core.api.teachers

import com.goldenpiedevs.schedule.app.core.dao.BaseResponseModel
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoTeacherModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TeachersService {
    @GET("groups/{id}/teachers")
    fun getTeachers(@Path("id") id: String): Deferred<Response<BaseResponseModel<ArrayList<DaoTeacherModel>>>>
}