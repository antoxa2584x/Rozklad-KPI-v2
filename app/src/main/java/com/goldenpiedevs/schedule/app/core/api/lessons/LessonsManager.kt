package com.goldenpiedevs.schedule.app.core.api.lessons

import android.content.Context
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.api.group.GroupManager
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoDayModel
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoTeacherModel
import com.goldenpiedevs.schedule.app.core.notifications.manger.NotificationManager
import com.goldenpiedevs.schedule.app.core.utils.preference.AppPreference
import com.goldenpiedevs.schedule.app.core.utils.util.NO_INTERNET
import com.goldenpiedevs.schedule.app.core.utils.util.RESULT_FAILED
import com.goldenpiedevs.schedule.app.core.utils.util.RESULT_OK
import com.goldenpiedevs.schedule.app.core.utils.util.isNetworkAvailable
import kotlinx.coroutines.*
import org.jetbrains.anko.toast

class LessonsManager(private val context: Context, private val lessonsService: LessonsService, private val groupManager: GroupManager, private val notificationManager: NotificationManager) {
    fun loadTimeTableAsync(groupName: String): Deferred<Boolean> = GlobalScope.async {
        val response = lessonsService.getGroupTimeTable(groupName).await()

        if (response.isSuccessful) {
            val group = groupManager.getGroupInfoAsync(groupName).await()

            response.body()?.let {
                DaoDayModel.saveGroupTimeTable(it.data!!, group!!.groupFullName, notificationManager)
            } ?: return@async false

            AppPreference.apply {
                group?.let {
                    isFirstLaunch = false
                    this.groupName = it.groupFullName
                    groupId = it.groupId

                }
            }
        }

        response.isSuccessful
    }

    fun loadTimeTableAsync(groupID: Int) = loadTimeTableAsync(groupID.toString())

    fun loadTeacherTimeTableAsync(teacherId: Int, listener: (Int) -> Unit) {
        GlobalScope.launch {
            val teacher = DaoTeacherModel.getTeacher(teacherId)

            teacher?.let {
                if (it.hasLoadedSchedule) {
                    listener(RESULT_OK)
                    return@launch
                }
            }

            if (!context.isNetworkAvailable()) {
                listener(NO_INTERNET)

                launch(Dispatchers.Main) {
                    context.toast(R.string.no_internet)
                }

                return@launch
            }

            with(lessonsService.getTeacherTimeTable(teacherId).await()) {
                if (isSuccessful) {
                    val body = body()

                    body?.data?.let {
                        DaoDayModel.saveTeacherTimeTable(it, teacherId)
                    }

                    teacher?.apply {
                        hasLoadedSchedule = true
                    }?.save()
                }

                launch(Dispatchers.Main) {
                    listener(if (isSuccessful) RESULT_OK else RESULT_FAILED)
                }
            }
        }
    }
}