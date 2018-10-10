package com.goldenpiedevs.schedule.app.core.api.lessons

import com.goldenpiedevs.schedule.app.core.api.group.GroupManager
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoDayModel
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoTeacherModel
import com.goldenpiedevs.schedule.app.core.notifications.manger.NotificationManager
import com.goldenpiedevs.schedule.app.core.utils.AppPreference
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.async

class LessonsManager(private val lessonsService: LessonsService, private val groupManager: GroupManager, private val notificationManager: NotificationManager) {
    fun loadTimeTable(groupID: String): Deferred<Boolean> = GlobalScope.async {
        val response = lessonsService.getGroupTimeTable(groupID).await()

        if (response.isSuccessful) {
            val group = groupManager.getGroupInfo(groupID).await()

            response.body()?.let {
                DaoDayModel.saveGroupTimeTable(it.data!!, group!!.groupFullName, notificationManager)
            } ?: return@async false

            AppPreference.apply {
                group?.let { it ->
                    isFirstLaunch = false
                    groupName = it.groupFullName
                    groupId = it.groupId

                }
            }
        }

        response.isSuccessful
    }

    fun loadTimeTable(groupID: Int) = loadTimeTable(groupID.toString())

    fun loadTeacherTimeTable(teacherId: Int): Deferred<Boolean> = GlobalScope.async {
        val response = lessonsService.getTeacherTimeTable(teacherId).await()

        if (response.isSuccessful) {
            val body = response.body()!!

            body.data?.let {
                DaoDayModel.saveTeacherTimeTable(it, teacherId)
            }

            DaoTeacherModel.getTeacher(teacherId).apply {
                hasLoadedSchedule = true
                save()
            }
        }

        response.isSuccessful
    }
}