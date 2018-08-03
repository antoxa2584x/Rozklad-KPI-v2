package com.goldenpiedevs.schedule.app.ui.main

import android.support.v4.app.FragmentManager
import com.goldenpiedevs.schedule.app.R.id.container
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl
import com.goldenpiedevs.schedule.app.ui.timetable.TimeTableFragment

class MainImplementation : BasePresenterImpl<MainView>(), MainPresenter {
    private lateinit var supportFragmentManager: FragmentManager

    override fun setSupportFragmentManager(supportFragmentManager: FragmentManager) {
        this.supportFragmentManager = supportFragmentManager
    }

    override fun onTimeTableClick() {
        supportFragmentManager.beginTransaction().replace(container, TimeTableFragment()).commit()
    }

    override fun onMapClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGroupChangeClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSettingsClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTeachersClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}