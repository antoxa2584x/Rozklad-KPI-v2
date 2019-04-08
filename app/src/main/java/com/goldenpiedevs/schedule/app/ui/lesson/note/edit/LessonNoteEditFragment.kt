package com.goldenpiedevs.schedule.app.ui.lesson.note.edit

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ui.lesson.note.adapter.NotePhotosAdapter
import com.goldenpiedevs.schedule.app.ui.lesson.note.base.BaseLessonNoteFragment
import com.goldenpiedevs.schedule.app.ui.lesson.note.base.BaseLessonNotePresenter
import com.goldenpiedevs.schedule.app.ui.lesson.note.base.BaseLessonNoteView
import kotlinx.android.synthetic.main.lesson_edit_note_layout.*

class LessonNoteEditFragment : BaseLessonNoteFragment(), LessonNoteEditView {

    override fun getFragmentLayout() = R.layout.lesson_edit_note_layout

    private var presenter = LessonNoteEditImplementation()

    override fun getParentPresenter(): BaseLessonNotePresenter<BaseLessonNoteView> {
        return presenter as BaseLessonNotePresenter<BaseLessonNoteView>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.setEditMode(true)
        setHasOptionsMenu(true)
    }

    override fun setNoteText(note: String) {
        note_text.setText(note)
    }

    override fun setAdapter(adapter: NotePhotosAdapter, layoutManager: RecyclerView.LayoutManager) {
        note_photo_list.layoutManager = layoutManager
        note_photo_list.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save_note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_note -> {
                presenter.saveNote()
                true
            }
            R.id.delete_note -> {
                presenter.deleteNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun getNote() = note_text.text?.toString()

}