package com.goldenpiedevs.schedule.app.core.dao.note

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class DaoNoteModel : RealmObject() {
    var lessonId: String = ""
    var note: String = ""
    var photos: RealmList<String> = RealmList()
}