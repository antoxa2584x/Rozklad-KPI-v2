package com.goldenpiedevs.schedule.app.ui.lesson

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoLessonModel
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl

class LessonImplementation : BasePresenterImpl<LessonView>(), LessonPresenter {
    companion object {
        const val LESSON_ID = "LESSON_ID"
    }

    private lateinit var daoLessonModel: DaoLessonModel

    override fun showLessonData(bundle: Bundle) {
        daoLessonModel = DaoLessonModel().getLesson(bundle.getInt(LESSON_ID))

        with(view) {
            showLessonName(daoLessonModel.lessonFullName)
            showLessonTime(daoLessonModel.getTime())
            showLessonType(daoLessonModel.lessonType)

            if (!daoLessonModel.teachers.isEmpty()) {
                showLessonTeachers(daoLessonModel.teachers.let { it ->
                    var string = ""
                    it.map { it.teacherFullName }.forEach { string += if (string.isEmpty()) it else "\n$it" }
                    string
                })
            }

            with(daoLessonModel.rooms){
                if(isEmpty())
                    return

                this.first()?.let {
                    showLessonRoom(it.roomName)

                    if (ContextCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED)
                        showLessonLocation(it.getGeoPoint())
                }

            }

            daoLessonModel.noteModel?.let {
                showNoteText(it.note)
                showNotePhotos(it.photos)
            }
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