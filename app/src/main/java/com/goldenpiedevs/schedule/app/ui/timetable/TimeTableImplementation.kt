package com.goldenpiedevs.schedule.app.ui.timetable

import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoWeekModel
import com.goldenpiedevs.schedule.app.core.dao.timetable.DayModel
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl
import com.vicpin.krealmextensions.query
import io.realm.RealmList
import org.threeten.bp.LocalDateTime

class TimeTableImplementation : BasePresenterImpl<TimeTableView>(), TimeTablePresenter {
    private lateinit var firstWeekDays: RealmList<DayModel>
    private lateinit var secondWeekDays: RealmList<DayModel>

    override fun getData() {
        firstWeekDays = DaoWeekModel().query { equalTo("week_number", 1.toString()) }.first().days!!
        secondWeekDays = DaoWeekModel().query { equalTo("week_number", 2.toString()) }.first().days!!

        firstWeekDays.addChangeListener { _: RealmList<DayModel>? -> updateCurrentDay() }
        secondWeekDays.addChangeListener { _: RealmList<DayModel>? -> updateCurrentDay() }

        view.showFirstWeekData(firstWeekDays)
        view.showSecondWeekData(secondWeekDays)
    }

    private fun updateCurrentDay() {
        val isFirstWeek = true //TODO
        val currentDay: Int

        currentDay = (if (isFirstWeek) firstWeekDays else secondWeekDays)
                .let { it.indexOf(getCurrentDayModel(it)) }

        view.showCurrentDay(isFirstWeek, currentDay)
    }

    private fun getCurrentDayModel(collection: RealmList<DayModel>): DayModel =
            collection.find {
                it.dayNumber == LocalDateTime.now().dayOfWeek.value
            } ?: DayModel()

    override fun onLessonClicked(id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun detachView() {
        super.detachView()
        firstWeekDays.removeAllChangeListeners()
        secondWeekDays.removeAllChangeListeners()
    }
}