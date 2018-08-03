package com.goldenpiedevs.schedule.app.ui.main

import android.os.Bundle
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ui.base.BaseActivity
import com.goldenpiedevs.schedule.app.ui.base.BasePresenter
import com.goldenpiedevs.schedule.app.ui.base.BaseView

class MainActivity : BaseActivity(), MainView {

    override fun <T : BasePresenter<V>, V : BaseView> getPresenter(): T = presenter as T

    lateinit var presenter: MainPresenter

    override fun getActivityLayout(): Int = R.layout.main_activity_layout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = MainImplementation()
        presenter.setSupportFragmentManager(supportFragmentManager)
        presenter.onTimeTableClick()
    }

    override fun toggleToolbarCollapseMode(isCollapsing: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}