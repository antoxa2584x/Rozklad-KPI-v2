package com.goldenpiedevs.schedule.app.core.dao.note

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class NoteModel() : RealmObject() {
    var lessonId: String? = null
    var note: String? = null
    var photos: RealmList<String>? = null
}