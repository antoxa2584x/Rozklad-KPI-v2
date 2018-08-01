package com.goldenpiedevs.schedule.app

import android.support.multidex.MultiDexApplication
import com.chibatching.kotpref.Kotpref

class ScheduleApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this)
    }
}