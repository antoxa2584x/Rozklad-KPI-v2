package com.goldenpiedevs.schedule.app.ui.lesson.note.edit

import com.goldenpiedevs.schedule.app.ui.lesson.note.base.BaseLessonNotePresenter

interface LessonNoteEditPresenter : BaseLessonNotePresenter<LessonNoteEditView> {
    fun saveNote()
    fun deleteNote()
}