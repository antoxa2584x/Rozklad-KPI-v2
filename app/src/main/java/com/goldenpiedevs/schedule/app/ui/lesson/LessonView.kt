package com.goldenpiedevs.schedule.app.ui.lesson

import com.goldenpiedevs.schedule.app.core.dao.note.DaoNotePhoto
import com.goldenpiedevs.schedule.app.ui.base.BaseView
import io.realm.OrderedRealmCollection
import org.osmdroid.util.GeoPoint

interface LessonView : BaseView {
    fun showLessonName(string: String)
    fun showLessonTeachers(string: String)
    fun showLessonRoom(string: String)
    fun showLessonType(string: String)
    fun showLessonLocation(geoPoint: GeoPoint)
    fun showLessonTime(string: String)

    fun attachNoteView()
    fun attachEditNoteView()
}