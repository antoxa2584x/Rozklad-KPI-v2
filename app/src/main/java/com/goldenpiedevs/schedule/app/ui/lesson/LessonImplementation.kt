package com.goldenpiedevs.schedule.app.ui.lesson

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.api.lessons.LessonsManager
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoLessonModel
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl
import com.goldenpiedevs.schedule.app.ui.fragment.keeper.FragmentKeeperActivity
import com.goldenpiedevs.schedule.app.ui.timetable.TimeTableFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
        GlobalScope.launch {
            daoLessonModel = DaoLessonModel.getLesson(bundle.getString(LESSON_ID)!!)

            launch(Dispatchers.Main) {
                with(view) {
                    showLessonName(daoLessonModel.lessonName)
                    showLessonTime(daoLessonModel.getTime())
                    showLessonType(daoLessonModel.lessonType)

                    with(daoLessonModel.teachers) {
                        if (!isEmpty()) {
                            showLessonTeachers(asSequence()
                                    .map { it.teacherName }
                                    .joinToString(separator = "\n"))
                        }
                    }

                    with(daoLessonModel.rooms) {
                        if (isEmpty())
                            return@with

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
        }
    }

    override fun onNoteSave() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTeacherClick() {
        when (daoLessonModel.teachers.size) {
            1 -> loadTeacherInfoOrOpen(null)
            else -> showTeacherSelectDialog()
        }
    }

    private fun showTeacherSelectDialog() {
        AlertDialog.Builder(view.getContext()).apply {
            setTitle(R.string.teacher)

            setItems(daoLessonModel.teachers.map { it.teacherName }.toTypedArray()) { _, which ->
                loadTeacherInfoOrOpen(daoLessonModel.teachers[which]!!.teacherId)
            }

            setNegativeButton(android.R.string.cancel, null)
            create()
            show()
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
            val result = lessonsManager.loadTeacherTimeTableAsync(teacherId.toInt()).await()
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