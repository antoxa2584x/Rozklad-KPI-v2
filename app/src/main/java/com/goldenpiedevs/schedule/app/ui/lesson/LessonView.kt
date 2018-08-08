package com.goldenpiedevs.schedule.app.ui.lesson

import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoTeacherModel
import com.goldenpiedevs.schedule.app.ui.base.BaseView
import io.realm.OrderedRealmCollection
import org.osmdroid.util.GeoPoint

interface LessonView : BaseView {
    fun onNoteEditClick()
    fun showLessonName(string: String)
    fun showLessonTeachers(teachers: OrderedRealmCollection<DaoTeacherModel>)
    fun showLessonRoom(string: String)
    fun showLessonType(string: String)
    fun showLessonLocation(geoPoint: GeoPoint)
    fun showLessonTime(string: String)
    fun showNoteText(string: String)
    fun showNotePhotos(fileNames: OrderedRealmCollection<String>)
}