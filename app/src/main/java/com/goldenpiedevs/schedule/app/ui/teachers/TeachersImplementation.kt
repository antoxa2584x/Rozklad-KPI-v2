package com.goldenpiedevs.schedule.app.ui.teachers

import com.goldenpiedevs.schedule.app.core.api.lessons.LessonsManager
import com.goldenpiedevs.schedule.app.core.api.teachers.TeachersManager
import com.goldenpiedevs.schedule.app.core.dao.group.DaoGroupModel
import com.goldenpiedevs.schedule.app.core.utils.preference.AppPreference
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl
import com.goldenpiedevs.schedule.app.ui.fragment.keeper.FragmentKeeperActivity
import com.goldenpiedevs.schedule.app.ui.timetable.TimeTableFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class TeachersImplementation : BasePresenterImpl<TeachersView>(), TeachersPresenter {

    @Inject
    lateinit var teachersManager: TeachersManager
    @Inject
    lateinit var lessonsManager: LessonsManager

    override fun loadTeachers() {
        view.showTeachersData(DaoGroupModel.getAllForTeachers(AppPreference.groupId))

        GlobalScope.launch {
            teachersManager.loadTeachersAsync(AppPreference.groupId.toString()).await()
        }
    }

    override fun onTeacherClick(teacherId: String) {
        loadTeacherSchedule(teacherId)
    }

    private fun loadTeacherSchedule(teacherId: String) {
        view.showProgressDialog()

        GlobalScope.launch {
            val result = lessonsManager.loadTeacherTimeTableAsync(teacherId.toInt()).await()
            launch(Dispatchers.Main) {
                view.dismissProgressDialog()

                if (result)
                    openTeacherSchedule(teacherId)
                else {
                    //TODO
                }
            }
        }
    }

    private fun openTeacherSchedule(teacherId: String) {
        view.getContext().startActivity<FragmentKeeperActivity>(TimeTableFragment.TEACHER_ID to teacherId)
    }
}