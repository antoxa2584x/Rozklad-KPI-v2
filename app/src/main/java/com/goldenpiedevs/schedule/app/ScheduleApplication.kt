package com.goldenpiedevs.schedule.app

import android.support.multidex.MultiDexApplication
import com.chibatching.kotpref.Kotpref
import io.realm.Realm
import io.realm.RealmConfiguration

class ScheduleApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this)
        Realm.init(this)

        val config = RealmConfiguration.Builder()
                .name("schedule.realm")
                .schemaVersion(0)
                .build()

        Realm.setDefaultConfiguration(config)
    }
}