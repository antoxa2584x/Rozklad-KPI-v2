package com.goldenpiedevs.schedule.app.ui.lesson.note.base

import androidx.recyclerview.widget.RecyclerView
import com.goldenpiedevs.schedule.app.ui.base.BaseView
import com.goldenpiedevs.schedule.app.ui.lesson.note.adapter.NotePhotosAdapter

interface BaseLessonNoteView : BaseView {
    fun setAdapter(adapter: NotePhotosAdapter, layoutManager: RecyclerView.LayoutManager)
    fun setNoteText(note: String)
}