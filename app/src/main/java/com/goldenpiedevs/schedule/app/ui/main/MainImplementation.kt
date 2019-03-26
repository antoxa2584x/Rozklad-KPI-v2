package com.goldenpiedevs.schedule.app.ui.main

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
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
import com.google.android.material.navigation.NavigationView
import org.jetbrains.anko.startActivity
import java.util.*
import kotlin.concurrent.schedule


class MainImplementation : BasePresenterImpl<MainView>(), MainPresenter {

    private lateinit var supportFragmentManager: androidx.fragment.app.FragmentManager
    private lateinit var navigationView: NavigationView

    override fun setSupportFragmentManager(supportFragmentManager: androidx.fragment.app.FragmentManager) {
        this.supportFragmentManager = supportFragmentManager

        supportFragmentManager.addOnBackStackChangedListener {
            supportFragmentManager.findFragmentById(R.id.container)?.let {
                checkItem()
            }
        }
    }

    override fun setNavigationView(navigationView: NavigationView) {
        this.navigationView = navigationView
    }

    override fun showTimeTable() {
        supportFragmentManager.beginTransaction()
                .add(container, TimeTableFragment())
                .commit()

        checkItem()
    }

    override fun onTimeTableClick() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
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
        view.toggleToolbarCollapseMode(false)

        supportFragmentManager.beginTransaction()
                .add(container, fragment)
                .addToBackStack(fragment::class.java.canonicalName)
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

    override fun onExamsClick() {
        with(view.getContext()) {
            val browserIntent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://rozklad.org.ua/exams/group/${AppPreference.groupName}"))
            startActivity(browserIntent)
        }
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

    override fun checkItem() {
        with(navigationView) {
            if (supportFragmentManager.fragments.isNotEmpty()) {
                when (supportFragmentManager.fragments.last()::class.java.canonicalName) {
                    TimeTableFragment::class.java.canonicalName -> {
                        setCheckedItem(R.id.timetable)
                        view.showMenu(true)
                    }
                    TeachersFragment::class.java.canonicalName -> {
                        setCheckedItem(R.id.teachers)
                        view.showMenu(false)
                    }
                    MapFragment::class.java.canonicalName -> {
                        setCheckedItem(R.id.map)
                        view.showMenu(false)
                    }
                }
            } else {
                setCheckedItem(R.id.timetable)
                view.showMenu(true)
            }
        }
    }

    override fun onResume() {
        view.setActivitySubtitle(AppPreference.groupName.toUpperCase())
        super.onResume()
    }
}