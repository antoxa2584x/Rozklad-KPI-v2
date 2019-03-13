package com.goldenpiedevs.schedule.app.ui.preference

import androidx.fragment.app.FragmentManager
import com.goldenpiedevs.schedule.app.ui.base.BasePresenter

interface PreferencePresenter : BasePresenter<PreferenceView> {
    fun setSupportFragmentManager(supportFragmentManager: androidx.fragment.app.FragmentManager)
    fun openNotificationPreference()
    fun onBackPressed()
}