package com.goldenpiedevs.schedule.app.ui.lesson

import android.os.Bundle
import com.goldenpiedevs.schedule.app.core.dao.timetable.LessonModel
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl

class LessonImplementation : BasePresenterImpl<LessonView>(), LessonPresenter {
    companion object {
        const val LESSON_ID = "LESSON_ID"
    }

    private lateinit var lessonModel: LessonModel

    override fun showLessonData(bundle: Bundle) {
        lessonModel = LessonModel().getLesson(bundle.getInt(LESSON_ID))

        val room = lessonModel.rooms.first()!!
        val noteModel = lessonModel.noteModel!!

        with(view) {
            showLessonName(lessonModel.lessonFullName)
            showLessonTime(lessonModel.getTime())
            showLessonType(lessonModel.lessonType)
            showLessonTeachers(lessonModel.teachers)
            showLessonRoom(room.roomName)
            showLessonLocation(room.getGeoPoint())
            showNoteText(noteModel.note)
            showNotePhotos(noteModel.photos)
        }
    }

    override fun onNoteSave() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNoteEditView() {
        if (lessonModel.hasNote) {

        } else {

        }
    }
}