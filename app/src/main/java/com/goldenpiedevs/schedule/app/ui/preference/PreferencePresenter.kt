package com.goldenpiedevs.schedule.app.ui.preference

import androidx.fragment.app.FragmentManager
import com.goldenpiedevs.schedule.app.ui.base.BasePresenter

interface PreferencePresenter : BasePresenter<PreferenceView> {
    fun setSupportFragmentManager(supportFragmentManager: FragmentManager)
    fun openNotificationPreference()
}