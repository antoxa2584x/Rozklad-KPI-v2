package com.goldenpiedevs.schedule.app.ui.launcher

import android.widget.AutoCompleteTextView
import com.goldenpiedevs.schedule.app.ui.base.BasePresenter

interface LauncherPresenter : BasePresenter<LauncherView> {
    fun showNextScreen()

    fun onGroupNameInputUpdated(input: String)

    fun setAutocompleteTextView(autoCompleteTextView: AutoCompleteTextView)
}