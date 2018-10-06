package com.goldenpiedevs.schedule.app.ui.timetable

import android.os.Bundle
import com.goldenpiedevs.schedule.app.core.dao.timetable.*
import com.goldenpiedevs.schedule.app.core.ext.isFirstWeek
import com.goldenpiedevs.schedule.app.core.ext.today
import com.goldenpiedevs.schedule.app.core.utils.AppPreference
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl
import com.goldenpiedevs.schedule.app.ui.lesson.LessonActivity
import com.goldenpiedevs.schedule.app.ui.lesson.LessonImplementation
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.android.Main
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.startActivity
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.temporal.IsoFields
import java.util.*

class TimeTableImplementation : BasePresenterImpl<TimeTableView>(), TimeTablePresenter {

    private lateinit var data: MutableList<DaoDayModel>
    private var groupId = AppPreference.groupId
    private var lastUpdate = AppPreference.lastTimeTableUpdate

    override fun getData(arguments: Bundle?) {
        GlobalScope.launch {
            var week1: List<DaoDayModel> = listOf()
            var week2: List<DaoDayModel> = listOf()

            with(DaoDayModel.getLessons()) {
                when (arguments != null && arguments.containsKey(TimeTableFragment.TEACHER_ID)) {
                    true -> {
                        with(forTeacher(arguments!!.getString(TimeTableFragment.TEACHER_ID))) {
                            week1 = forWeek(1)
                            week2 = forWeek(2)
                        }
                    }
                    false -> {
                        with(forGroupWithName(AppPreference.groupName)) {
                            week1 = forWeek(1)
                            week2 = forWeek(2)
                        }
                    }
                }
            }

            if (::data.isInitialized) {
                data.clear()
            } else {
                data = mutableListOf()
            }

            data.apply {
                if (week1.isNotEmpty()) {
                    add(DaoDayModel())
                    addAll(week1)
                }
                if (week2.isNotEmpty()) {
                    add(DaoDayModel())
                    addAll(week2)
                }
            }

            launch(Dispatchers.Main) {
                view.showWeekData(data)

                getCurrentDay(isFirstWeek, today.dayOfWeek.value)
            }
        }
    }

    override fun onToolbarClick() {
        getCurrentDay(isFirstWeek, today.dayOfWeek.value)
    }

    private fun getCurrentDay(week: Boolean, day: Int) {
        val currentDay: Int = data.indexOf(
                data.find {
                    it.dayNumber.toInt() == day &&
                            it.lessons.first()!!.lessonWeek.toInt() == if (week) 1 else 2
                })

        with(currentDay) {
            if (this < 0)
                return@with

            view.showDay(this)
        }
    }

    override fun onLessonClicked(id: String) {
        view.getContext().startActivity<LessonActivity>(LessonImplementation.LESSON_ID to id)
    }

    override fun scrollToDay(dateClicked: Date?) =
            with(LocalDateTime.ofEpochSecond(dateClicked!!.time / 1000L,
                    0, ZoneOffset.MAX).toLocalDate()) {
                getCurrentDay(get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) % 2 == 0, dayOfWeek.value)
            }

    override fun onResume() {
        if (groupId != AppPreference.groupId || lastUpdate != AppPreference.lastTimeTableUpdate) {
            with(AppPreference){
                this@TimeTableImplementation.groupId = groupId
                lastUpdate = lastTimeTableUpdate
            }

            view.clearTimetable()
            getData(null)
        }

        super.onResume()
    }
}
