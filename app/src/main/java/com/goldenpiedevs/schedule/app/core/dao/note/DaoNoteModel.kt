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

    var lessonId: Int = 0
    var groupId: Int = 0
    var note: String = ""
    var photos: RealmList<DaoNotePhoto> = RealmList()

    fun save(photos: MutableList<DaoNotePhoto>) {
        val realm = Realm.getDefaultInstance()

        realm.executeTransaction {
            val photoRealmList = RealmList<DaoNotePhoto>()
            photoRealmList.addAll(photos)
            this.photos = photoRealmList

            it.copyToRealmOrUpdate(this)
        }

        if (!realm.isClosed)
            realm.close()

    }

    fun delete() {
        val lessonModel = DaoLessonModel.getLesson(lessonId)

        val realm = Realm.getDefaultInstance()

        getManaged(lessonId, groupId, realm)?.let { note ->
            realm.executeTransaction {
                note.deleteFromRealm()
                it.copyToRealmOrUpdate(lessonModel)
            }
        }

        if (!realm.isClosed)
            realm.close()
    }

    companion object {
        private fun getManaged(lessonId: Int, groupId: Int, realm: Realm) =
                realm.where(DaoNoteModel::class.java)
                        .equalTo("groupId", groupId)
                        .equalTo("lessonId", lessonId)
                        .findFirst()

        fun get(lessonId: Int, groupId: Int): DaoNoteModel {
            val realm = Realm.getDefaultInstance()
            val managedNote = getManaged(lessonId, groupId, realm)

            val noteCopy = managedNote?.let { realm.copyFromRealm(it) }

            if (!realm.isClosed)
                realm.close()

            return noteCopy?.let { it } ?: DaoNoteModel().apply {
                this.lessonId = lessonId
                this.groupId = groupId
            }
        }

        fun exist(lessonId: Int, groupId: Int): Boolean {
            val realm = Realm.getDefaultInstance()
            val exist = realm.where(DaoNoteModel::class.java)
                    .equalTo("groupId", groupId)
                    .equalTo("lessonId", lessonId)
                    .count() > 0

            if (!realm.isClosed)
                realm.close()

            return exist
        }
    }
}