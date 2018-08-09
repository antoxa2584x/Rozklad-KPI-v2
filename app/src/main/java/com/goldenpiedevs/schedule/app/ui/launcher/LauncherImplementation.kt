package com.goldenpiedevs.schedule.app.ui.launcher

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.goldenpiedevs.schedule.app.core.utils.AppPreference
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl
import com.goldenpiedevs.schedule.app.ui.choose.group.ChooseGroupActivity
import com.goldenpiedevs.schedule.app.ui.main.MainActivity
import java.util.*
import kotlin.concurrent.schedule

class LauncherImplementation : BasePresenterImpl<LauncherView>(), LauncherPresenter {
    override fun showNextScreen() {
        Timer().schedule(500) {
            with(view as AppCompatActivity) {
                if (AppPreference.isFirstLaunch) {
                    startActivity(Intent(this, ChooseGroupActivity::class.java))
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                }

                Timer().schedule(200) {
                    finish()
                }
            }
        }
    }
}