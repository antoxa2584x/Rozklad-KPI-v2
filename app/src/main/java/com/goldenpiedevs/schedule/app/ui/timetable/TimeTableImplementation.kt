package com.goldenpiedevs.schedule.app.ui.timetable

import android.content.Intent
import android.os.Bundle
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoDayModel
import com.goldenpiedevs.schedule.app.core.ext.isFirstWeek
import com.goldenpiedevs.schedule.app.core.ext.today
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl
import com.goldenpiedevs.schedule.app.ui.lesson.LessonActivity
import com.goldenpiedevs.schedule.app.ui.lesson.LessonImplementation
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.temporal.IsoFields
import java.util.*

class TimeTableImplementation : BasePresenterImpl<TimeTableView>(), TimeTablePresenter {

    private lateinit var data: List<DaoDayModel>

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun getData(arguments: Bundle?) {

        arguments?.let {
            when{
                arguments.containsKey(TimeTableFragment.TEACHER_ID)->{
                    data = mutableListOf<DaoDayModel>().apply {
                        add(DaoDayModel())
                        addAll(DaoDayModel.firstWeekForTeacher(arguments.getString(TimeTableFragment.TEACHER_ID)))
                        add(DaoDayModel())
                        addAll(DaoDayModel.secondWeekForTeacher(arguments.getString(TimeTableFragment.TEACHER_ID)))
                    }
                }
            }
        }?:run {
            data = mutableListOf<DaoDayModel>().apply {
                add(DaoDayModel())
                addAll(DaoDayModel.firstWeek())
                add(DaoDayModel())
                addAll(DaoDayModel.secondWeek())
            }
        }

        view.showWeekData(data)
    }

    override fun showCurrentDay() {
        getCurrentDay(isFirstWeek, today.dayOfWeek.value)
    }

    private fun getCurrentDay(week: Boolean, day: Int) {
        val currentDay: Int = data.indexOf(
                data.find {
                    it.dayNumber.toInt() == day &&
                            it.lessons.first()!!.lessonWeek.toInt() == if (week) 1 else 2
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