package com.goldenpiedevs.schedule.app.ui.fragment.keeper

import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoTeacherModel
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl
import com.goldenpiedevs.schedule.app.ui.timetable.TimeTableFragment

class FragmentKeeperImplementation : BasePresenterImpl<FragmentKeeperView>(), FragmentKeeperPresenter {

    lateinit var supportFragmentManager: FragmentManager

    override fun setFragmentManager(fragmentManager: FragmentManager) {
        supportFragmentManager = fragmentManager
    }

    override fun showFragmentForBundle(bundle: Bundle?) {
        bundle?.let {
            when {
                it.containsKey(TimeTableFragment.TEACHER_ID) ->
                    showTeacherTimeTableFragment(it.getString(TimeTableFragment.TEACHER_ID)!!)
            }
        }
    }

    private fun showTeacherTimeTableFragment(string: String) {
        view.setTitle(DaoTeacherModel.getTeacher(string.toInt()).teacherName) //TODO
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, TimeTableFragment.getInstance(string))
                .commit()
    }
}