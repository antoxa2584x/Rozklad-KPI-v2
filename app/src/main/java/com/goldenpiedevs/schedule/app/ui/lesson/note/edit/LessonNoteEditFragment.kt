package com.goldenpiedevs.schedule.app.ui.lesson.note.edit

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ui.lesson.note.adapter.NotePhotosAdapter
import com.goldenpiedevs.schedule.app.ui.lesson.note.base.BaseLessonNoteFragment
import com.goldenpiedevs.schedule.app.ui.lesson.note.base.BaseLessonNotePresenter
import com.goldenpiedevs.schedule.app.ui.lesson.note.base.BaseLessonNoteView
import kotlinx.android.synthetic.main.lesson_edit_note_layout.*

class LessonNoteEditFragment : BaseLessonNoteFragment(), LessonNoteEditView {

    override fun getFragmentLayout() = R.layout.lesson_edit_note_layout

    override fun getParentPresenter(): BaseLessonNotePresenter<BaseLessonNoteView> {
        return LessonNoteEditImplementation() as BaseLessonNotePresenter<BaseLessonNoteView>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.setEditMode(true)
    }

    override fun setNoteText(note: String) {
        note_text.setText(note)
    }

    override fun setAdapter(adapter: NotePhotosAdapter, layoutManager: RecyclerView.LayoutManager) {
        note_photo_list.layoutManager = layoutManager
        note_photo_list.adapter = adapter
    }
}