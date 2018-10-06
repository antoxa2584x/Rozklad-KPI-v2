package com.goldenpiedevs.schedule.app.ui.main

import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentManager
import com.goldenpiedevs.schedule.app.ui.base.BasePresenter
import java.util.*

interface MainPresenter : BasePresenter<MainView> {
    fun setSupportFragmentManager(supportFragmentManager: FragmentManager)
    fun setNavigationView(navigationView: NavigationView)

    fun showCurrentDayTitle()
    fun onTimeTableClick()
    fun onMapClick()
    fun onGroupChangeClick()
    fun onSettingsClick()
    fun onTeachersClick()
    fun onBackPressed()
    fun updateCalendarState()
    fun onCalendarOpen(firstDayOfNewMonth: Date)
}