package com.goldenpiedevs.schedule.app.ui.lesson.note.edit

import com.goldenpiedevs.schedule.app.ui.lesson.LessonActivity
import com.goldenpiedevs.schedule.app.ui.lesson.note.base.BaseLessonNoteImplementation

class LessonNoteEditImplementation : BaseLessonNoteImplementation<LessonNoteEditView>(), LessonNoteEditPresenter {
    override fun saveNote() {
        if (!view.getNote().isNullOrEmpty() || !adapter.getData().isEmpty()) {
            noteModel.apply {
                note = view.getNote() ?: ""
            }.save(adapter.getData())

            (view.getContext() as LessonActivity).saveNote()
        } else {
            deleteNote()
        }
    }

    override fun deleteNote() {
        noteModel.delete()

        (view.getContext() as LessonActivity).deleteNote()
    }
}