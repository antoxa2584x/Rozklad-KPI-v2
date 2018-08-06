package com.goldenpiedevs.schedule.app.ui.timetable

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.timetable.DayModel
import com.goldenpiedevs.schedule.app.ui.base.BaseFragment
import com.goldenpiedevs.schedule.app.ui.view.adapter.TimeTableAdapter
import io.realm.OrderedRealmCollection
import kotlinx.android.synthetic.main.time_table_layout.*


class TimeTableFragment : BaseFragment(), TimeTableView {

    private lateinit var timeTablePresenter: TimeTablePresenter

    override fun getFragmentLayout(): Int = R.layout.time_table_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timeTablePresenter = TimeTableImplementation()
        timeTablePresenter.attachView(this)

        firstWeekList.layoutManager = LinearLayoutManager(context)
        secondWeekList.layoutManager = LinearLayoutManager(context)

        timeTablePresenter.getData()
    }

    override fun showFirstWeekData(orderedRealmCollection: OrderedRealmCollection<DayModel>) {
        if (firstWeekList.adapter == null)
            firstWeekList.adapter = TimeTableAdapter(orderedRealmCollection)
    }

    override fun showSecondWeekData(orderedRealmCollection: OrderedRealmCollection<DayModel>) {
        if (secondWeekList.adapter == null)
            secondWeekList.adapter = TimeTableAdapter(orderedRealmCollection)
    }

    override fun showCurrentDay(isFirstWeek: Boolean, currentDay: Int) {
        if (isFirstWeek) {
            firstWeekList.scrollToPosition(currentDay)
        } else {
            secondWeekList.scrollToPosition(currentDay)
        }
    }

}