package com.goldenpiedevs.schedule.app.ui.lesson.note.base

import com.esafirm.imagepicker.model.Image
import com.goldenpiedevs.schedule.app.ui.base.BasePresenter

interface BaseLessonNotePresenter<T : BaseLessonNoteView> : BasePresenter<T> {
    fun setEditMode(inEditMode: Boolean)
    fun getNote(lessonId: String)
    fun onPhotosSelected(list: ArrayList<Image>)
}