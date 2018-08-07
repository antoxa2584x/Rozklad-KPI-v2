package com.goldenpiedevs.schedule.app.ui.launcher

import android.view.View
import android.widget.AutoCompleteTextView
import com.goldenpiedevs.schedule.app.ui.base.BasePresenter

interface LauncherPresenter : BasePresenter<LauncherView> {
    fun showNextScreen():Boolean
    fun setAutocompleteTextView(autoCompleteTextView: AutoCompleteTextView)
    fun blurView(view: View)
}