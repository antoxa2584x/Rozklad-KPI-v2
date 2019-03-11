package com.goldenpiedevs.schedule.app.ui.main

import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.R.id.container
import com.goldenpiedevs.schedule.app.core.ext.currentWeek
import com.goldenpiedevs.schedule.app.core.ext.getCurrentMonth
import com.goldenpiedevs.schedule.app.core.ext.todayName
import com.goldenpiedevs.schedule.app.core.utils.preference.AppPreference
import com.goldenpiedevs.schedule.app.ui.base.BaseFragment
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl
import com.goldenpiedevs.schedule.app.ui.map.MapFragment
import com.goldenpiedevs.schedule.app.ui.preference.PreferenceActivity
import com.goldenpiedevs.schedule.app.ui.teachers.TeachersFragment
import com.goldenpiedevs.schedule.app.ui.timetable.TimeTableFragment
import org.jetbrains.anko.startActivity
import java.util.*
import kotlin.concurrent.schedule

class MainImplementation : BasePresenterImpl<MainView>(), MainPresenter {

    private lateinit var supportFragmentManager: FragmentManager
    private lateinit var navigationView: NavigationView

    override fun setSupportFragmentManager(supportFragmentManager: FragmentManager) {
        this.supportFragmentManager = supportFragmentManager
    }

    override fun setNavigationView(navigationView: NavigationView) {
        this.navigationView = navigationView
    }

    override fun showTimeTable() {
        navigationView.setCheckedItem(R.id.timetable)

        supportFragmentManager.beginTransaction()
                .replace(container, TimeTableFragment())
                .commit()
    }

    override fun onTimeTableClick() {
        Timer().schedule(300) {
            (view as AppCompatActivity).onBackPressed()
        }
    }

    override fun showCurrentDayTitle() {
        view.setActivitySubtitle(AppPreference.groupName.toUpperCase())

        with(view) {
            //String immutable, seems it does not like apply
            var title = todayName
            title = title.substring(0, 1).toUpperCase() + title.substring(1)
            title += ", ${currentWeek + 1} ${getContext().getString(R.string.week)}"

            setActivityTitle(title)
        }
    }

    override fun onMapClick() {
        changeFragment(MapFragment())
    }

    private fun <T : BaseFragment> changeFragment(fragment: T) {
        navigationView.setCheckedItem(R.id.timetable)
        view.toggleToolbarCollapseMode(false)

        supportFragmentManager.beginTransaction()
                .add(container, fragment)
                .addToBackStack(null)
                .commit()
    }
    override fun onCalendarOpen(firstDayOfNewMonth: Date) {
        with(view) {
            setActivityTitle(getContext().resources.getStringArray(R.array.months)[firstDayOfNewMonth.getCurrentMonth()])
        }
    }

    override fun updateCalendarState() {
        AppPreference.isCalendarOpen = !AppPreference.isCalendarOpen
        view.showCalendar(AppPreference.isCalendarOpen)
    }

    override fun onGroupChangeClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSettingsClick() {
        Timer().schedule(200) {
            with(view.getContext() as AppCompatActivity) {
                startActivity<PreferenceActivity>()
            }
        }
    }

    override fun onTeachersClick() {
        changeFragment(TeachersFragment())
    }

    override fun onResume() {
        view.setActivitySubtitle(AppPreference.groupName.toUpperCase())
        super.onResume()
    }
}