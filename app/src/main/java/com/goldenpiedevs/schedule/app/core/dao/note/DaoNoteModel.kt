package com.goldenpiedevs.schedule.app.core.dao.note

import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoLessonModel
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
open class DaoNoteModel : RealmObject() {
    @PrimaryKey
    var id = UUID.randomUUID().toString()

    var lessonId: String = ""
    var groupId: Int = 0
    var note: String = ""
    var photos: RealmList<DaoNotePhoto> = RealmList()

    fun save(photos: MutableList<DaoNotePhoto>) {
        val lessonModel = DaoLessonModel.getLesson(lessonId)
        val realm = Realm.getDefaultInstance()

        realm.executeTransaction {
            val photoRealmList = RealmList<DaoNotePhoto>()
            photoRealmList.addAll(photos)
            this.photos = photoRealmList

            it.copyToRealmOrUpdate(this)

            lessonModel.noteModel = this

            it.copyToRealmOrUpdate(lessonModel)
        }

        if (!realm.isClosed)
            realm.close()

    }

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

            return group?.let { it } ?: DaoNoteModel().apply {
                this.lessonId = lessonId
                this.groupId = groupId
            }
        }
    }
}