package com.goldenpiedevs.schedule.app.ui.lesson.note.show

import androidx.recyclerview.widget.RecyclerView
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ui.lesson.note.adapter.NotePhotosAdapter
import com.goldenpiedevs.schedule.app.ui.lesson.note.base.BaseLessonNoteFragment
import com.goldenpiedevs.schedule.app.ui.lesson.note.base.BaseLessonNotePresenter
import com.goldenpiedevs.schedule.app.ui.lesson.note.base.BaseLessonNoteView
import kotlinx.android.synthetic.main.lesson_note_layout.*

class LessonNoteFragment : BaseLessonNoteFragment(), LessonNoteView {
    override fun getFragmentLayout() = R.layout.lesson_note_layout

    override fun getParentPresenter(): BaseLessonNotePresenter<BaseLessonNoteView> {
        return LessonNoteImplementation() as BaseLessonNotePresenter<BaseLessonNoteView>
    }
    override fun setNoteText(note: String) {
        note_text.text = note
    }

    override fun setAdapter(adapter: NotePhotosAdapter, layoutManager: RecyclerView.LayoutManager) {
        note_photo_list.layoutManager = layoutManager
        note_photo_list.adapter = adapter
    }
}