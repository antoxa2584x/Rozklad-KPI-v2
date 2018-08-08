package com.goldenpiedevs.schedule.app.ui.launcher

import android.os.Bundle
import com.goldenpiedevs.schedule.app.ui.base.BaseActivity

class LauncherActivity : BaseActivity<LauncherPresenter, LauncherView>(), LauncherView {

    private lateinit var presenter: LauncherPresenter

    override fun getPresenterChild(): LauncherPresenter = presenter

    override fun getActivityLayout(): Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = LauncherImplementation()

        with(presenter) {
            attachView(this@LauncherActivity)
            showNextScreen()
        }
    }
}