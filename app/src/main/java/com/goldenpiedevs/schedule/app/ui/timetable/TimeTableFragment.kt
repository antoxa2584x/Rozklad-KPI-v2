package com.goldenpiedevs.schedule.app.ui.timetable

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoDayModel
import com.goldenpiedevs.schedule.app.ui.base.BaseFragment
import com.goldenpiedevs.schedule.app.ui.view.adapter.TimeTableAdapter
import io.realm.OrderedRealmCollection
import kotlinx.android.synthetic.main.main_activity_layout.*
import kotlinx.android.synthetic.main.time_table_layout.*
import java.util.*


class TimeTableFragment : BaseFragment(), TimeTableView, CompactCalendarView.CompactCalendarViewListener {

    private lateinit var presenter: TimeTablePresenter

    override fun getFragmentLayout(): Int = R.layout.time_table_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView(firstWeekList, secondWeekList)

        presenter = TimeTableImplementation()

        with(presenter) {
            attachView(this@TimeTableFragment)
            getData()
            showCurrentDay()
        }

        activity!!.compactCalendarView.setListener(this)
    }

    override fun onDayClick(dateClicked: Date?) {
       presenter.scrollToDay(dateClicked)
    }

    override fun onMonthScroll(firstDayOfNewMonth: Date?) {}

    private fun setUpRecyclerView(vararg recyclerView: RecyclerView) {
        recyclerView.forEach {
            it.isNestedScrollingEnabled = false
            it.layoutManager = LinearLayoutManager(context)
        }
    }

    override fun showWeekData(isFirstWeek: Boolean, orderedRealmCollection: OrderedRealmCollection<DaoDayModel>) {
        (if (isFirstWeek) firstWeekList else secondWeekList).apply {
            adapter ?: run {
                adapter = TimeTableAdapter(orderedRealmCollection) { presenter.onLessonClicked(it) }
            }
        }
    }

    override fun showCurrentDay(isFirstWeek: Boolean, currentDay: Int, forceDisableAnimDelay: Boolean) {
        (if (isFirstWeek) firstWeekList else secondWeekList).let {
            it.post {
                presenter.scrollToView(activity!!.findViewById(R.id.appbar), baseScrollView, it.getChildAt(currentDay), forceDisableAnimDelay)
            }
        }
    }
}