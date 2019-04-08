package com.goldenpiedevs.schedule.app.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.utils.preference.AppPreference
import com.goldenpiedevs.schedule.app.ui.base.BaseActivity
import com.goldenpiedevs.schedule.app.ui.widget.ScheduleWidgetProvider
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.main_activity_layout.*
import kotlinx.android.synthetic.main.navigation_layout.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : BaseActivity<MainPresenter, MainView>(), MainView, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var presenter: MainPresenter
    private var showMenu = true

    override fun getPresenterChild(): MainPresenter = presenter

    override fun getActivityLayout(): Int = R.layout.main_activity_layout

    companion object {
        const val SHOW_MENU = "SHOW_MENU"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = MainImplementation()

        with(presenter) {
            attachView(this@MainActivity)
            showCurrentDayTitle()
            setSupportFragmentManager(supportFragmentManager)
            setNavigationView(navView)

            if (savedInstanceState == null)
                showTimeTable()
            else {
                showMenu = savedInstanceState.getBoolean(SHOW_MENU)
            }
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

        action_settings.setOnClickListener {
            presenter.onSettingsClick()
            drawerLayout.closeDrawers()
        }

        ScheduleWidgetProvider.updateWidget(getContext())
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.calendar)?.isVisible = showMenu

        compactCalendarView.visibility = when {
            !showMenu -> {
                presenter.showCurrentDayTitle()
                View.GONE
            }
            AppPreference.isCalendarOpen -> {
                presenter.onCalendarOpen(compactCalendarView.firstDayOfCurrentMonth)
                View.VISIBLE
            }
            else -> {
                presenter.showCurrentDayTitle()
                View.GONE
            }
        }

        menu?.findItem(R.id.calendar)?.setIcon(
                if (AppPreference.isCalendarOpen) {
                    R.drawable.ic_calendar_view_day
                } else {
                    R.drawable.ic_calendar
                })

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.timetable_menu, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawers()

        if (!item.isChecked) {
            when (item.itemId) {
                R.id.timetable -> presenter.onTimeTableClick()
                R.id.exams -> presenter.onExamsClick()
                R.id.map -> presenter.onMapClick()
                R.id.teachers -> presenter.onTeachersClick()
            }
        }

        return true
    }

    override fun setActivityTitle(string: String) {
        toolbar.title = string
    }

    override fun setActivitySubtitle(string: String) {
        toolbar.subtitle = string
    }

    override fun showCalendar(calendarOpen: Boolean) {
        compactCalendarView.visibility = if (calendarOpen) {
            presenter.onCalendarOpen(compactCalendarView.firstDayOfCurrentMonth)
            View.VISIBLE
        } else {
            presenter.showCurrentDayTitle()
            View.GONE
        }
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SHOW_MENU, showMenu)
    }

    override fun toggleToolbarCollapseMode(isCollapsing: Boolean) {}


    override fun showMenu(showMenu: Boolean) {
        this@MainActivity.showMenu = showMenu
        invalidateOptionsMenu()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START) && !navView.menu.getItem(0).isChecked) {
            drawerLayout.closeDrawers()
            return
        }

        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()

            presenter.checkItem()
        } else {
            super.onBackPressed()
        }
    }
}