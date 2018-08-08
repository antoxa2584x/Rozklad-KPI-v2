package com.goldenpiedevs.schedule.app.ui.timetable

import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoDayModel
import com.goldenpiedevs.schedule.app.ui.base.BaseView
import io.realm.OrderedRealmCollection

interface TimeTableView : BaseView {
    fun showWeekData(isFirstWeek: Boolean, orderedRealmCollection: OrderedRealmCollection<DaoDayModel>)
    fun showCurrentDay(isFirstWeek: Boolean, currentDay: Int)
}