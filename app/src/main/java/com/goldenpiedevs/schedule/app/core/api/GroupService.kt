package com.goldenpiedevs.schedule.app.core.api

import com.goldenpiedevs.schedule.app.core.api.utils.ToJson
import com.goldenpiedevs.schedule.app.core.dao.GropuListResponse
import com.goldenpiedevs.schedule.app.core.dao.GroupModel
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Part
import retrofit2.http.Query

interface GroupService {
    @GET("groups")
    fun searchGroupList(@ToJson @Query("search") groupName: Map<String, String>): Observable<Response<GropuListResponse>>

    @GET("groups")
    fun getGroupList(@ToJson @Query("filter") groupName: Map<String, String>): Response<GropuListResponse>

    @GET("groups/{id}")
    fun getGroup(@Part("id") groupName: Int): Observable<Response<GroupModel>>

    companion object {
        const val LIMIT = "limit"
        const val OFFSET = "offset"
        const val QUERY = "query"
    }
}