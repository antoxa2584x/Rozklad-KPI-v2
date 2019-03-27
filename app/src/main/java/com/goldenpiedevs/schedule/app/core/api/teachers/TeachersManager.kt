package com.goldenpiedevs.schedule.app.core.api.teachers

import android.content.Context
import com.goldenpiedevs.schedule.app.core.dao.group.DaoGroupModel
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoTeacherModel
import com.goldenpiedevs.schedule.app.core.utils.util.isNetworkAvailable
import io.realm.Realm
import io.realm.RealmList
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TeachersManager(private val context: Context, private val teachersService: TeachersService) {
    fun loadTeachers(groupId: Int) {
        if (!context.isNetworkAvailable()) {
            return
        }

        GlobalScope.launch {
            this@TeachersManager.loadTeachersAsync(groupId)
        }
    }

    fun loadTeachersAsync(groupId: Int): Deferred<Boolean> = GlobalScope.async {
        val response = teachersService.getTeachers(groupId).await()

        if (response.isSuccessful) {
            val realm = Realm.getDefaultInstance()
            val group = DaoGroupModel.getGroup(groupId)

            response.body()?.data?.let {

                val list = RealmList<DaoTeacherModel>().apply {
                    addAll(response.body()?.data!!)
                }

                list.onEach { newTeacher ->
                    DaoTeacherModel.getTeacher(newTeacher.teacherId)?.let { managedTeacher ->
                        newTeacher.hasLoadedSchedule = managedTeacher.hasLoadedSchedule
                    }
                }

                realm.executeTransaction { r ->

                    r.copyToRealmOrUpdate(list)

                    group?.let {
                        r.copyToRealmOrUpdate(
                                it.apply {
                                    teachers = list
                                })
                    }
                }

                if (!realm.isClosed)
                    realm.close()
            }
        }

        return@async response.isSuccessful
    }
}