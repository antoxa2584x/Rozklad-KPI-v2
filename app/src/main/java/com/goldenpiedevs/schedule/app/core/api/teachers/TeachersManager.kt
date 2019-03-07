package com.goldenpiedevs.schedule.app.core.api.teachers

import com.goldenpiedevs.schedule.app.core.dao.group.DaoGroupModel
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoTeacherModel
import io.realm.Realm
import io.realm.RealmList
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class TeachersManager(private val teachersServise: TeachersService) {
    fun loadTeachersAsync(groupId: String): Deferred<Boolean> = GlobalScope.async {
        val response = teachersServise.getTeachers(groupId).await()

        if (response.isSuccessful) {
            val realm = Realm.getDefaultInstance()
            val group = DaoGroupModel.getGroup(groupId)

            response.body()?.data?.let {
                realm.executeTransaction { r ->
                    val list = RealmList<DaoTeacherModel>().apply {
                        addAll(response.body()?.data!!)
                    }

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

        response.isSuccessful
    }
}