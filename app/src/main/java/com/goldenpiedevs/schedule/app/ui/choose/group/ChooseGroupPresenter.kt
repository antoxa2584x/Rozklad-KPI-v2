package com.goldenpiedevs.schedule.app.ui.choose.group

import android.view.View
import android.widget.AutoCompleteTextView
import com.goldenpiedevs.schedule.app.ui.base.BasePresenter

interface ChooseGroupPresenter : BasePresenter<ChooseGroupView> {
    fun setAutocompleteTextView(autoCompleteTextView: AutoCompleteTextView)
    fun blurView(view: View)
}