package com.goldenpiedevs.schedule.app.ui.main

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.utils.AppPreference
import com.goldenpiedevs.schedule.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.main_activity_layout.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : BaseActivity<MainPresenter, MainView>(), MainView, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var presenter: MainPresenter
    private var showMenu = true

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
        }

        val actionBarDrawerToggle = ActionBarDrawerToggle(
                this,
                drawerLayout,
                findViewById(R.id.toolbar),
                0,
                0
        )

        compactCalendarView.setDayColumnNames(arrayOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Нд"))

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        action_settings.setOnClickListener { presenter.openSettings() }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu!!.findItem(R.id.calendar).isVisible = showMenu

        compactCalendarView.visibility = when {
            !showMenu -> View.GONE
            AppPreference.isCalebdarOpen -> View.VISIBLE
            else -> View.GONE
        }

        menu.findItem(R.id.calendar).setIcon(
                if (AppPreference.isCalebdarOpen)
                    R.drawable.ic_calendar_view_day
                else R.drawable.ic_calendar)

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.timetable_menu, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (!item.isChecked) {
            showMenu = false
            when (item.itemId) {
                R.id.timetable -> {
                    onBackPressed()
                    showMenu = true
                }
                R.id.map -> presenter.onMapClick()
            }
            invalidateOptionsMenu()
        }

        drawerLayout.closeDrawers()

        return true
    }

    override fun setActivityTitle(string: String) {
        toolbar.title = string
    }

    override fun setActivitySubtitle(string: String) {
        toolbar.subtitle = string
    }

    override fun showCalendar(calendarOpen: Boolean) {
        compactCalendarView.visibility = if (calendarOpen) View.VISIBLE else View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            R.id.calendar -> {
                presenter.updateCalendarState()
                invalidateOptionsMenu()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun toggleToolbarCollapseMode(isCollapsing: Boolean) {

    }

    override fun onBackPressed() {
        presenter.onBackPressed()
        showMenu = true
        invalidateOptionsMenu()
    }
}