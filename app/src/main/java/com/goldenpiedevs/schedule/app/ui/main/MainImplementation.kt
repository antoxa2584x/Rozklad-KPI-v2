package com.goldenpiedevs.schedule.app.ui.main

import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.R.id.container
import com.goldenpiedevs.schedule.app.core.api.lessons.LessonsManager
import com.goldenpiedevs.schedule.app.core.ext.currentWeek
import com.goldenpiedevs.schedule.app.core.ext.todayName
import com.goldenpiedevs.schedule.app.core.utils.AppPreference
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl
import com.goldenpiedevs.schedule.app.ui.map.MapFragment
import com.goldenpiedevs.schedule.app.ui.timetable.TimeTableFragment
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class MainImplementation : BasePresenterImpl<MainView>(), MainPresenter {
    @Inject
    lateinit var lessonsManager: LessonsManager

    private lateinit var supportFragmentManager: FragmentManager
    private lateinit var navigationView: NavigationView

    override fun setSupportFragmentManager(supportFragmentManager: FragmentManager) {
        this.supportFragmentManager = supportFragmentManager
    }

    override fun setNavigationView(navigationView: NavigationView) {
        this.navigationView = navigationView
    }

    override fun onTimeTableClick() {
        navigationView.setCheckedItem(R.id.timetable)

        supportFragmentManager.beginTransaction()
                .replace(container, TimeTableFragment())
                .commit()
    }

    override fun attachView(view: MainView) {
        super.attachView(view)

        with(view) {
            setActivitySubtitle(AppPreference.groupName.toUpperCase())

            //String immutable, seems it does not like apply
            var title = todayName
            title = title.substring(0, 1).toUpperCase() + title.substring(1)
            title += ", ${currentWeek + 1} ${view.getContext().getString(R.string.week)}"

            setActivityTitle(title)
        }
    }

    override fun onMapClick() {
        navigationView.setCheckedItem(R.id.timetable)
        view.toggleToolbarCollapseMode(false)

        supportFragmentManager.beginTransaction()
                .add(container, MapFragment())
                .addToBackStack(null)
                .commit()
    }

    override fun onGroupChangeClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSettingsClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTeachersClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            navigationView.setCheckedItem(R.id.timetable)
            supportFragmentManager.popBackStack()
        } else {
            (view.getContext() as AppCompatActivity).finish()
        }
    }

    //Just get new data. Show next time)
    override fun loadTimeTable() {
//        GlobalScope.launch {
//            lessonsManager.loadTimeTable(AppPreference.groupId).await()
//        }
    }
}