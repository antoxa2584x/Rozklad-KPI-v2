package com.goldenpiedevs.schedule.app.ui.lesson

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.api.lessons.LessonsManager
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoLessonModel
import com.goldenpiedevs.schedule.app.core.utils.util.RESULT_OK
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl
import com.goldenpiedevs.schedule.app.ui.fragment.keeper.FragmentKeeperActivity
import com.goldenpiedevs.schedule.app.ui.lesson.note.base.BaseLessonNoteFragment
import com.goldenpiedevs.schedule.app.ui.lesson.note.edit.LessonNoteEditFragment
import com.goldenpiedevs.schedule.app.ui.lesson.note.show.LessonNoteFragment
import com.goldenpiedevs.schedule.app.ui.timetable.TimeTableFragment
import com.goldenpiedevs.schedule.app.ui.widget.ScheduleWidgetProvider
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
    private lateinit var supportFragmentManager: FragmentManager

    private var isInEditMode = false
    private var hasFinalNote = false
    private var hasInitialNote = false

    override fun isInEditMode() = isInEditMode

    override fun setFragmentManager(supportFragmentManager: FragmentManager) {
        this.supportFragmentManager = supportFragmentManager
    }

    override fun showLessonData(bundle: Bundle) {
        GlobalScope.launch {
            daoLessonModel = DaoLessonModel.getUniqueLesson(bundle.getString(LESSON_ID)!!)

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

                    if (daoLessonModel.haveNote) {
                        hasInitialNote = true
                        hasFinalNote = true

                        attachNoteView()
                    }
                }
            }
        }
    }

    override fun onTeacherClick() {
        when (daoLessonModel.teachers.size) {
            1 -> loadTeacherSchedule(daoLessonModel.teachers.first()!!.teacherId)
            else -> showTeacherSelectDialog()
        }
    }

    private fun showTeacherSelectDialog() {
        AlertDialog.Builder(view.getContext()).apply {
            setTitle(R.string.teacher)

            setItems(daoLessonModel.teachers.map { it.teacherName }.toTypedArray()) { _, which ->
                loadTeacherSchedule(daoLessonModel.teachers[which]!!.teacherId)
            }

            setNegativeButton(android.R.string.cancel, null)
            create()
            show()
        }
    }

    override fun onTeacherClick(id: Int) {
        loadTeacherSchedule(id)
    }

    private fun openTeacherSchedule(teacherId: Int) {
        view.getContext().startActivity<FragmentKeeperActivity>(TimeTableFragment.TEACHER_ID to teacherId)
    }

    private fun loadTeacherSchedule(teacherId: Int) {
        view.showProgressDialog()

        lessonsManager.loadTeacherTimeTableAsync(teacherId) {
            view.dismissProgressDialog()

            if (it == RESULT_OK)
                openTeacherSchedule(teacherId)
            else {
                //TODO
            }
        }
    }

    override fun onEditNoteClick() {
        if (isInEditMode)
            attachNoteView()
        else
            attachEditNoteView()
    }

    override fun onNoteSaved() {
        attachNoteView()
        hasFinalNote = true
    }

    override fun onNoteDeleted() {
        removeAllNoteViews()
        hasFinalNote = false
    }

    private fun removeAllNoteViews() {
        with(supportFragmentManager) {
            findFragmentById(R.id.lesson_note_view_container)?.let {
                beginTransaction().remove(it).commit()
            }

            popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        isInEditMode = false

        (view.getIt() as AppCompatActivity).invalidateOptionsMenu()
    }

    override fun attachEditNoteView() {
        supportFragmentManager.apply {
            popBackStackImmediate()

            beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.lesson_note_view_container,
                            BaseLessonNoteFragment.getInstance(daoLessonModel.lessonId, true),
                            LessonNoteEditFragment::class.java.canonicalName)
                    .commit()
        }

        isInEditMode = true

        (view.getIt() as AppCompatActivity).invalidateOptionsMenu()
    }

    override fun attachNoteView() {
        supportFragmentManager.apply {
            popBackStackImmediate()

            beginTransaction()
                    .replace(R.id.lesson_note_view_container,
                            BaseLessonNoteFragment.getInstance(daoLessonModel.lessonId),
                            LessonNoteFragment::class.java.canonicalName)
                    .commit()
        }

        isInEditMode = false

        (view.getIt() as AppCompatActivity).invalidateOptionsMenu()
    }

    override fun onBackPressed() {
        if (hasInitialNote != hasFinalNote) {
            with(view.getIt() as LessonActivity) {
                ScheduleWidgetProvider.updateWidget(view.getContext())
                setResult(Activity.RESULT_OK, intent)
            }
        }

        super.onBackPressed()
    }
}