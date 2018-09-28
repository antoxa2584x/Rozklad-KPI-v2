package com.goldenpiedevs.schedule.app.ui.timetable

import android.os.Bundle
import android.view.View
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoDayModel
import com.goldenpiedevs.schedule.app.ui.base.BaseFragment
import com.goldenpiedevs.schedule.app.ui.view.LinearLayoutManagerWithSmoothScroller
import com.goldenpiedevs.schedule.app.ui.view.adapter.TimeTableAdapter
import kotlinx.android.synthetic.main.main_activity_layout.*
import kotlinx.android.synthetic.main.recyler_view_layout.*
import java.util.*


class TimeTableFragment : BaseFragment(), TimeTableView, CompactCalendarView.CompactCalendarViewListener {

    companion object {
        const val TEACHER_ID = "teacher_id"

        fun getInstance(teacherId: String) = TimeTableFragment().apply {
            arguments = Bundle().apply {
                putString(TEACHER_ID, teacherId)
            }
        }
    }

    private lateinit var presenter: TimeTablePresenter

    override fun getFragmentLayout(): Int = R.layout.recyler_view_layout


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = TimeTableImplementation()

        with(presenter) {
            attachView(this@TimeTableFragment)
            getData()
            showCurrentDay()
        }

        list.apply {
            layoutManager = LinearLayoutManagerWithSmoothScroller(context)
        }

        activity!!.compactCalendarView.setListener(this)
    }

    override fun onDayClick(dateClicked: Date?) {
        presenter.scrollToDay(dateClicked)
    }

    override fun onMonthScroll(firstDayOfNewMonth: Date?) {}

    override fun showWeekData(data: List<DaoDayModel>) {
        list.adapter ?: run {
            list.adapter = TimeTableAdapter(data, context) { presenter.onLessonClicked(it) }
        }
    }

    override fun showDay(currentDay: Int) {
        list.let {
            it?.postDelayed({
                it.smoothScrollToPosition(currentDay)
            }, 100)
        }
    }
}