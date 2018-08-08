package com.goldenpiedevs.schedule.app.ui.launcher

import com.goldenpiedevs.schedule.app.ui.base.BasePresenter

interface LauncherPresenter : BasePresenter<LauncherView> {
    fun showNextScreen()
}