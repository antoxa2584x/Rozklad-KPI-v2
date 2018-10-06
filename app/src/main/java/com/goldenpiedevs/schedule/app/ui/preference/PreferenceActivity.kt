package com.goldenpiedevs.schedule.app.ui.preference

import android.os.Bundle
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ui.base.BaseActivity

class PreferenceActivity : BaseActivity<PreferencePresenter, PreferenceView>(), PreferenceView {
    override fun getPresenterChild(): PreferencePresenter = presenter

    override fun getActivityLayout(): Int = R.layout.preference_activity_layout

    private var presenter = PreferenceImplementation()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
        }

        with(presenter) {
            attachView(this@PreferenceActivity)
            setSupportFragmentManager(supportFragmentManager)
            openNotificationPreference()
        }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
}