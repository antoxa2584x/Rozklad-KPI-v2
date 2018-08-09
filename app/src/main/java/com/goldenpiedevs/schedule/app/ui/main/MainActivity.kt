package com.goldenpiedevs.schedule.app.ui.main

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.main_activity_layout.*

class MainActivity : BaseActivity<MainPresenter, MainView>(), MainView, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var presenter: MainPresenter

    override fun getPresenterChild(): MainPresenter = presenter

    override fun getActivityLayout(): Int = R.layout.main_activity_layout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = MainImplementation()

        with(presenter) {
            attachView(this@MainActivity)
            setSupportFragmentManager(supportFragmentManager)
            setNavigationView(navView)
            onTimeTableClick()

            loadTimeTable()
        }

        val actionBarDrawerToggle = ActionBarDrawerToggle(
                this,
                drawerLayout,
                findViewById(R.id.toolbar),
                0,
                0
        )

        navView.setCheckedItem(R.id.timetable)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (!item.isChecked)
            when (item.itemId) {
                R.id.timetable -> onBackPressed()
                R.id.map -> presenter.onMapClick()
            }

        drawerLayout.closeDrawers()

        return true
    }

    override fun setActivityTitle(string: String) {
        collapsingToolbar.title = string
    }

    override fun setActivitySubtitle(string: String) {
        collapsingToolbar.subtitle = string
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun toggleToolbarCollapseMode(isCollapsing: Boolean) {
        appbar.setExpanded(isCollapsing, true)
        appbar.isActivated = isCollapsing
    }

    override fun onBackPressed() = presenter.onBackPressed()
}