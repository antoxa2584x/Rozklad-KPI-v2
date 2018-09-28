package com.goldenpiedevs.schedule.app.ui.lesson

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.goldenpiedevs.schedule.app.core.api.lessons.LessonsManager
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoLessonModel
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl
import com.goldenpiedevs.schedule.app.ui.fragment.keeper.FragmentKeeperActivity
import com.goldenpiedevs.schedule.app.ui.timetable.TimeTableFragment
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.android.Main
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class LessonImplementation : BasePresenterImpl<LessonView>(), LessonPresenter {
    companion object {
        const val LESSON_ID = "LESSON_ID"
    }

    @Inject
    lateinit var lessonsManager: LessonsManager

    private lateinit var daoLessonModel: DaoLessonModel

    override fun showLessonData(bundle: Bundle) {
        daoLessonModel = DaoLessonModel.getLesson(bundle.getInt(LESSON_ID))

        with(view) {
            showLessonName(daoLessonModel.lessonFullName)
            showLessonTime(daoLessonModel.getTime())
            showLessonType(daoLessonModel.lessonType)

            if (!daoLessonModel.teachers.isEmpty()) {
                showLessonTeachers(daoLessonModel.teachers
                        .asSequence()
                        .map { it.teacherFullName }
                        .joinToString(separator = "\n")
                        .dropLast(1))
            }

            with(daoLessonModel.rooms) {
                if (isEmpty())
                    return

                first()?.let {
                    showLessonRoom(it.roomName)

                    if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
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

    override fun onTeacherClick() {
        when (daoLessonModel.teachers.size) {
            1 -> loadTeacherInfoOrOpen(null)
            else -> view.showTeacherSelectDialog()
        }
    }

    override fun onTeacherClick(id: String) {
        loadTeacherInfoOrOpen(id)
    }

    private fun loadTeacherInfoOrOpen(id: String?) {
        val teacher = if (id.isNullOrEmpty()) {
            daoLessonModel.teachers.first()
        } else {
            daoLessonModel.teachers.find { it.teacherId == id }
        }

        teacher?.let {
            if (it.hasLoadedSchedule) {
                openTeacherSchedule(it.teacherId)
            } else {
                loadTeacherSchedule(it.teacherId)
            }
        }

    }

    private fun openTeacherSchedule(teacherId: String) {
        view.getContext().startActivity<FragmentKeeperActivity>(TimeTableFragment.TEACHER_ID to teacherId)
    }

    private fun loadTeacherSchedule(teacherId: String) {
        view.showProgressDialog()

        GlobalScope.launch {
            val result = lessonsManager.loadTeacherTimeTable(teacherId.toInt()).await()
            launch(Dispatchers.Main) {
                view.dismissProgressDialog()

                if (result)
                    openTeacherSchedule(teacherId)
                else {
                    //TODO
                }
            }
        }
    }

    override fun showNoteEditView() {
        if (daoLessonModel.hasNote) {
            //TODO
        } else {
            //TODO
        }
    }
}