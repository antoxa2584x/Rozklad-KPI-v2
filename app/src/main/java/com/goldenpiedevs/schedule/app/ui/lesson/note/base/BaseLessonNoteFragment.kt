package com.goldenpiedevs.schedule.app.ui.lesson.note.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.esafirm.imagepicker.features.ImagePicker
import com.goldenpiedevs.schedule.app.ui.base.BaseFragment
import com.goldenpiedevs.schedule.app.ui.lesson.note.edit.LessonNoteEditFragment
import com.goldenpiedevs.schedule.app.ui.lesson.note.show.LessonNoteFragment

abstract class BaseLessonNoteFragment : BaseFragment(), BaseLessonNoteView {

    companion object {
        private const val LESSON_ID = "lesson_id"

        fun getInstance(lessonId: String, editMode: Boolean = false): BaseFragment {
            val bundle = Bundle().apply {
                putString(LESSON_ID, lessonId)
            }
            return (if (editMode) LessonNoteEditFragment() else LessonNoteFragment())
                    .apply { arguments = bundle }
        }
    }

    protected lateinit var presenter: BaseLessonNotePresenter<BaseLessonNoteView>

    abstract fun getParentPresenter(): BaseLessonNotePresenter<BaseLessonNoteView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = getParentPresenter()

        presenter.attachView(this@BaseLessonNoteFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getNote(arguments!!.getString(LESSON_ID)!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val images = ArrayList(ImagePicker.getImages(data))

            presenter.onPhotosSelected(images)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

}