package com.goldenpiedevs.schedule.app.ui.teachers

import com.goldenpiedevs.schedule.app.ui.base.BasePresenter

interface TeachersPresenter : BasePresenter<TeachersView> {
    fun loadTeachers()
    fun onTeacherClick(teacherId: Int)
}