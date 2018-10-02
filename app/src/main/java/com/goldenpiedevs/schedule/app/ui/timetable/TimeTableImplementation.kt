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

    private lateinit var data: List<DaoDayModel>

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun getData(arguments: Bundle?) {

        var lessons: Sequence<DaoDayModel>

        GlobalScope.launch {
            lessons = DaoDayModel.getLessons()

            arguments?.let {
                when {
                    arguments.containsKey(TimeTableFragment.TEACHER_ID) -> {
                        data = mutableListOf<DaoDayModel>().apply {
                            add(DaoDayModel())
                            addAll(lessons.forWeek(1).forTeacher(arguments.getString(TimeTableFragment.TEACHER_ID)))
                            add(DaoDayModel())
                            addAll(lessons.forWeek(2).forTeacher(arguments.getString(TimeTableFragment.TEACHER_ID)))
                        }
                    }
                }
            } ?: run {
                data = mutableListOf<DaoDayModel>().apply {
                    add(DaoDayModel())
                    addAll(lessons.forWeek(1).forGroupWithName(AppPreference.groupName))
                    add(DaoDayModel())
                    addAll(lessons.forWeek(2).forGroupWithName(AppPreference.groupName))
                }
            }
            launch(Dispatchers.Main) {
                view.showWeekData(data)

                getCurrentDay(isFirstWeek, today.dayOfWeek.value)
            }
        }
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

    override fun onLessonClicked(id: String) {
        view.getContext().startActivity<LessonActivity>(LessonImplementation.LESSON_ID to id)
    }

    override fun scrollToDay(dateClicked: Date?) {
        val localDate = LocalDateTime.ofEpochSecond(dateClicked!!.time / 1000L, 0, ZoneOffset.MAX).toLocalDate()
        val week = localDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) % 2 == 0
        val day = localDate.dayOfWeek.value

        getCurrentDay(week, day)
    }
}
