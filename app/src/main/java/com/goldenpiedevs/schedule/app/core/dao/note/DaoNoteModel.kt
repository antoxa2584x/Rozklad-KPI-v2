package com.goldenpiedevs.schedule.app.core.dao.note

import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class DaoNoteModel : RealmObject() {
    var lessonId: String = ""
    var groupId: Int = 0
    var note: String = ""
    var photos: RealmList<DaoNotePhoto> = RealmList()

    companion object {
        fun get(lessonId: String, groupId: Int): DaoNoteModel {
            val realm = Realm.getDefaultInstance()
            val groupDao = realm.where(DaoNoteModel::class.java)
                    .equalTo("groupId", groupId)
                    .equalTo("lessonId", lessonId)
                    .findFirst()

            val group = groupDao?.let { realm.copyFromRealm(it) }

            if (!realm.isClosed)
                realm.close()

            return group?.let { it } ?: DaoNoteModel()
        }

    }
}