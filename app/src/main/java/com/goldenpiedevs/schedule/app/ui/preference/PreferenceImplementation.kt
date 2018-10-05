package com.goldenpiedevs.schedule.app.ui.preference

import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl

class PreferenceImplementation : BasePresenterImpl<PreferenceView>(), PreferencePresenter {

    private lateinit var supportFragmentManager: FragmentManager

    override fun setSupportFragmentManager(supportFragmentManager: FragmentManager) {
        this.supportFragmentManager = supportFragmentManager
    }

    override fun openNotificationPreference() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ApplicationPreferenceFragment())
                .commit()
    }

    override fun onBackPressed() {
        with(supportFragmentManager){
            if (backStackEntryCount > 0) {
                popBackStack()
            } else {
                (view.getContext() as AppCompatActivity).finish()
            }
        }
    }
}