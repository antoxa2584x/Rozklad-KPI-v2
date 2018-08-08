package com.goldenpiedevs.schedule.app.ui.main

import android.support.v4.app.FragmentManager
import com.goldenpiedevs.schedule.app.ui.base.BasePresenter

interface MainPresenter : BasePresenter<MainView> {
    fun setSupportFragmentManager(supportFragmentManager: FragmentManager)

    fun onTimeTableClick()
    fun onMapClick()
    fun onGroupChangeClick()
    fun onSettingsClick()
    fun onTeachersClick()
    fun onBackPressed()
    fun loadTimeTable()
}