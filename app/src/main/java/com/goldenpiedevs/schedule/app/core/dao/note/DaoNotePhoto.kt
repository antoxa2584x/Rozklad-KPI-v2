package com.goldenpiedevs.schedule.app.core.dao.note

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class DaoNotePhoto : RealmObject() {
    @PrimaryKey
    var id: Long = -1
    var path: String = ""
    var name: String = ""
}