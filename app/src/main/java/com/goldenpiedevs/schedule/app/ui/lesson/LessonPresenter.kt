package com.goldenpiedevs.schedule.app.ui.lesson

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.goldenpiedevs.schedule.app.ui.base.BasePresenter

interface LessonPresenter : BasePresenter<LessonView> {
    fun showLessonData(bundle: Bundle)
    fun onTeacherClick()
    fun onTeacherClick(id: String)
    fun onEditNoteClick()
    fun onNoteSaved()
    fun isInEditMode(): Boolean
    fun setFragmentManager(supportFragmentManager: FragmentManager)

    fun attachNoteView()
    fun attachEditNoteView()
    fun onNoteDeleted()
}