package com.goldenpiedevs.schedule.app.ui.main

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.R.id.toolbar
import com.goldenpiedevs.schedule.app.core.ext.lockAppBar
import com.goldenpiedevs.schedule.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.main_activity_layout.*

class MainActivity : BaseActivity<MainPresenter, MainView>(), MainView {
    private lateinit var presenter: MainPresenter

    override fun getPresenterChild(): MainPresenter = presenter

    override fun getActivityLayout(): Int = R.layout.main_activity_layout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = MainImplementation()

        with(presenter) {
            attachView(this@MainActivity)
            setSupportFragmentManager(supportFragmentManager)
            onTimeTableClick()

//            loadTimeTable() //TODO: Find crash if response is successful
        }

        val actionBarDrawerToggle = ActionBarDrawerToggle(
                this,
                drawerLayout,
                findViewById(toolbar),
                0,
                0
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

    }

    override fun setActivityTitle(string: String) {
        collapsingToolbar.title = string
    }

    override fun setActivitySubtitle(string: String) {
        collapsingToolbar.subtitle = string
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun toggleToolbarCollapseMode(isCollapsing: Boolean) {
        appbar.lockAppBar(isCollapsing)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
}