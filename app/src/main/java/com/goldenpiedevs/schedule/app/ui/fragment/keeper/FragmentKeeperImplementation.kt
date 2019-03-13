package com.goldenpiedevs.schedule.app.ui.fragment.keeper

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoTeacherModel
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl
import com.goldenpiedevs.schedule.app.ui.timetable.TimeTableFragment

class FragmentKeeperImplementation : BasePresenterImpl<FragmentKeeperView>(), FragmentKeeperPresenter {

    lateinit var supportFragmentManager: androidx.fragment.app.FragmentManager

    override fun setFragmentManager(fragmentManager: androidx.fragment.app.FragmentManager) {
        supportFragmentManager = fragmentManager
    }

    override fun showFragmentForBundle(bundle: Bundle?, savedInstanceState: Bundle?) {
        bundle?.let {
            when {
                it.containsKey(TimeTableFragment.TEACHER_ID) -> {
                    view.setTitle(DaoTeacherModel.getTeacher(it.getString(TimeTableFragment.TEACHER_ID)!!.toInt()).teacherName)

                    if (savedInstanceState == null)
                        showTeacherTimeTableFragment(it.getString(TimeTableFragment.TEACHER_ID)!!)
                }
            }
        }
    }

    private fun showTeacherTimeTableFragment(string: String) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, TimeTableFragment.getInstance(string))
                .commit()
    }
}