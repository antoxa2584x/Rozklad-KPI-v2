package com.goldenpiedevs.schedule.app.core.api.lessons

import com.goldenpiedevs.schedule.app.core.api.group.GroupManager
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoDayModel
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoTeacherModel
import com.goldenpiedevs.schedule.app.core.ext.saveBodyToDB
import com.goldenpiedevs.schedule.app.core.utils.AppPreference
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.async

class LessonsManager(private val lessonsService: LessonsService, private val groupManager: GroupManager) {
    fun loadTimeTable(groupID: Int): Deferred<Boolean> = GlobalScope.async {
        val response = lessonsService.getGroupTimeTable(groupID).await()

        if (response.isSuccessful) {
            val body = response.body()!!

            body.data?.saveBodyToDB()

            body.data?.groupBy { it.dayNumber.toInt() }?.forEach { (key, value) ->
                DaoDayModel.saveOrUpdate(key, value)
            }

            val group = groupManager.getGroupInfo(groupID).await()

            AppPreference.apply {
                group?.let {
                    isFirstLaunch = false
                    groupName = it.groupFullName
                    groupId = it.groupId
                }
            }
        }

        response.isSuccessful
    }

    fun loadTeacherTimeTable(teacherId: Int): Deferred<Boolean> = GlobalScope.async {
        val response = lessonsService.getTeacherTimeTable(teacherId).await()

        if (response.isSuccessful) {
            val body = response.body()!!
            body.data?.saveBodyToDB()

            DaoTeacherModel.getTeacher(teacherId).apply {
                hasLoadedSchedule = true
                save()
            }
        }

        response.isSuccessful
    }
}