package com.goldenpiedevs.schedule.app.core.api.group

import com.goldenpiedevs.schedule.app.core.api.utils.ToJson
import com.goldenpiedevs.schedule.app.core.dao.BaseResponseModel
import com.goldenpiedevs.schedule.app.core.dao.group.DaoGroupModel
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GroupService {
    @GET("groups")
    fun searchGroupList(@ToJson @Query("search") groupName: Map<String, String>): Observable<Response<BaseResponseModel<List<DaoGroupModel>>>>

    @GET("groups/{id}")
    fun getGroup(@Path("id") groupId: Int): Observable<Response<BaseResponseModel<DaoGroupModel>>>

    @GET("groups/{id}")
    fun getGroupInfo(@Path("id") groupName: String): Deferred<Response<BaseResponseModel<DaoGroupModel>>>

    companion object {
        const val LIMIT = "limit"
        const val OFFSET = "offset"
        const val QUERY = "query"
    }
}