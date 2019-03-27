package com.goldenpiedevs.schedule.app.ui.preference

import androidx.appcompat.app.AppCompatActivity
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl

class PreferenceImplementation : BasePresenterImpl<PreferenceView>(), PreferencePresenter {

    private lateinit var supportFragmentManager: androidx.fragment.app.FragmentManager

    override fun setSupportFragmentManager(supportFragmentManager: androidx.fragment.app.FragmentManager) {
        this.supportFragmentManager = supportFragmentManager
    }

    override fun openNotificationPreference() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ApplicationPreferenceFragment())
                .commit()
    }

    override fun onBackPressed() {
        with(supportFragmentManager) {
            if (backStackEntryCount > 0) {
                popBackStack()
            } else {
                (view.getContext() as AppCompatActivity).finish()
            }
        }
    }
}