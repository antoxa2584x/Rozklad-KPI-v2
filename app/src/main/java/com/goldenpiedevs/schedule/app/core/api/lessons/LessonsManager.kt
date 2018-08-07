package com.goldenpiedevs.schedule.app.core.api.lessons

import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoWeekModel
import com.goldenpiedevs.schedule.app.core.utils.AppPreference
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

class LessonsManager(private val lessonsService: LessonsService) {
    fun loadTimeTable(groupId: Int?): Deferred<Boolean> = async {
        val response = lessonsService.getGroupTimeTable(groupId).await()

        if (response.isSuccessful) {
            val body = response.body()!!
            DaoWeekModel().saveTimetable(body)

            AppPreference.apply {
                body.data!!.group!!.let {
                    isFirstLaunch = false
                    groupName = it.groupFullName!!
                    AppPreference.groupId = it.groupId!!
                }
            }
        }

        response.isSuccessful
    }
}