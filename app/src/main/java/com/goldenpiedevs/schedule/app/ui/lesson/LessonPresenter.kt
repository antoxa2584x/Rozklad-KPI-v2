package com.goldenpiedevs.schedule.app.ui.lesson

import android.os.Bundle
import com.goldenpiedevs.schedule.app.ui.base.BasePresenter

interface LessonPresenter : BasePresenter<LessonView> {
    fun showLessonData(bundle: Bundle)
    fun onNoteSave()
    fun showNoteEditView()
}