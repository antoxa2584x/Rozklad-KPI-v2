package com.goldenpiedevs.schedule.app.ui.preference

import android.support.v4.app.FragmentManager
import com.goldenpiedevs.schedule.app.ui.base.BasePresenter

interface PreferencePresenter : BasePresenter<PreferenceView> {
    fun setSupportFragmentManager(supportFragmentManager: FragmentManager)
    fun openNotificationPreference()
    fun onBackPressed()
}