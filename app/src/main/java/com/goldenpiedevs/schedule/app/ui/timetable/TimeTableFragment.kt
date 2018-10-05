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
import kotlinx.android.synthetic.main.toolbar.*
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
            getData(arguments)
        }

        list.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManagerWithSmoothScroller(context)
        }

        if (arguments == null) {
            activity!!.compactCalendarView.setListener(this)
        }

        activity!!.toolbar.setOnClickListener { presenter.onToolbarClick() }
    }

    override fun onDayClick(dateClicked: Date?) {
        presenter.scrollToDay(dateClicked)
    }

    override fun onMonthScroll(firstDayOfNewMonth: Date?) {}

    override fun showWeekData(data: MutableList<DaoDayModel>) {
        progressBar.visibility = View.GONE

        list.adapter?.let {
            (it as TimeTableAdapter).update(data)
        } ?: run {
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

    override fun clearTimetable() {
        list.adapter?.let {
            progressBar.visibility = View.VISIBLE
            (it as TimeTableAdapter).clear()
        }
    }

    override fun onResume() {
        presenter.onResume()
        super.onResume()
    }
}