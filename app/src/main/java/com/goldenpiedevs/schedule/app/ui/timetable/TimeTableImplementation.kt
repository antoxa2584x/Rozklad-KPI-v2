package com.goldenpiedevs.schedule.app.ui.timetable

import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoWeekModel
import com.goldenpiedevs.schedule.app.core.dao.timetable.DayModel
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl
import io.realm.Realm
import io.realm.RealmList
import org.threeten.bp.LocalDateTime

class TimeTableImplementation : BasePresenterImpl<TimeTableView>(), TimeTablePresenter {
    private lateinit var firstWeekDays: RealmList<DayModel>
    private lateinit var secondWeekDays: RealmList<DayModel>
    private val realm = Realm.getDefaultInstance()

    override fun getData() {

        firstWeekDays = realm.where(DaoWeekModel::class.java).equalTo("weekNumber", 1.toString()).findFirst()!!.days
        secondWeekDays =  realm.where(DaoWeekModel::class.java).equalTo("weekNumber", 2.toString()).findFirst()!!.days

        view.showFirstWeekData(firstWeekDays)
        view.showSecondWeekData(secondWeekDays)

        firstWeekDays.addChangeListener { _: RealmList<DayModel>? -> updateCurrentDay() }
        secondWeekDays.addChangeListener { _: RealmList<DayModel>? -> updateCurrentDay() }
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

        if(!realm.isClosed)
            realm.close()
    }
}