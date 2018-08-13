package com.goldenpiedevs.schedule.app.ui.timetable

import android.content.Intent
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoDayModel
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoWeekModel
import com.goldenpiedevs.schedule.app.core.ext.isFirstWeek
import com.goldenpiedevs.schedule.app.core.ext.today
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl
import com.goldenpiedevs.schedule.app.ui.lesson.LessonActivity
import com.goldenpiedevs.schedule.app.ui.lesson.LessonImplementation
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.temporal.IsoFields
import java.util.*
import kotlin.collections.ArrayList

class TimeTableImplementation : BasePresenterImpl<TimeTableView>(), TimeTablePresenter {

    private lateinit var data: ArrayList<DaoDayModel>

    override fun getData() {
        data = ArrayList<DaoDayModel>().apply {
            add(DaoDayModel())
            addAll(DaoWeekModel().getFirstWeekDays())
            add(DaoDayModel())
            addAll(DaoWeekModel().getSecondWeekDays())
        }

        view.showWeekData(data)
    }

    override fun showCurrentDay() {
        getCurrentDay(isFirstWeek, today.dayOfWeek.value)
    }

    private fun getCurrentDay(week: Boolean, day: Int) {
        val currentDay: Int = data.indexOf(
                data.find {
                    it.dayNumber == day
                            && it.lessons.first()!!.lessonWeek == if (week) 1 else 2
                })

        if (currentDay < 0)
            return

        view.showDay(currentDay)
    }

    override fun onLessonClicked(id: Int) {
        with(view.getContext()) {
            startActivity(Intent(this, LessonActivity::class.java)
                    .putExtra(LessonImplementation.LESSON_ID, id))
        }
    }

    override fun scrollToDay(dateClicked: Date?) {
        val localDate = LocalDateTime.ofEpochSecond(dateClicked!!.time / 1000L, 0, ZoneOffset.MAX).toLocalDate()
        val week = localDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) % 2 == 0
        val day = localDate.dayOfWeek.value

        getCurrentDay(week, day)
    }
}