package com.goldenpiedevs.schedule.app.ui.teachers

import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoTeacherModel
import com.goldenpiedevs.schedule.app.ui.base.BaseView
import io.realm.OrderedRealmCollection

interface TeachersView : BaseView {
    fun showTeachersData(data: OrderedRealmCollection<DaoTeacherModel>)
}