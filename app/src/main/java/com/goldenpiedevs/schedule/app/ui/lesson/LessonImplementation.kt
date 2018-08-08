package com.goldenpiedevs.schedule.app.ui.lesson

import android.os.Bundle
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoLessonModel
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl

class LessonImplementation : BasePresenterImpl<LessonView>(), LessonPresenter {
    companion object {
        const val LESSON_ID = "LESSON_ID"
    }

    private lateinit var daoLessonModel: DaoLessonModel

    override fun showLessonData(bundle: Bundle) {
        daoLessonModel = DaoLessonModel().getLesson(bundle.getInt(LESSON_ID))

        val room = daoLessonModel.rooms.first()!!
        val noteModel = daoLessonModel.noteModel

        with(view) {
            showLessonName(daoLessonModel.lessonFullName)
            showLessonTime(daoLessonModel.getTime())
            showLessonType(daoLessonModel.lessonType)
            showLessonTeachers(daoLessonModel.teachers)
            showLessonRoom(room.roomName)
            showLessonLocation(room.getGeoPoint())

            showNoteText(noteModel!!.note)
            showNotePhotos(noteModel.photos)
        }
    }

    override fun onNoteSave() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNoteEditView() {
        if (daoLessonModel.hasNote) {

        } else {

        }
    }
}