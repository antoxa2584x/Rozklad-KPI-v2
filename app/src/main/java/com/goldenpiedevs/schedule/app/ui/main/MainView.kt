package com.goldenpiedevs.schedule.app.ui.main

import com.goldenpiedevs.schedule.app.ui.base.BaseView

interface MainView : BaseView {
    fun toggleToolbarCollapseMode(isCollapsing: Boolean)
}
