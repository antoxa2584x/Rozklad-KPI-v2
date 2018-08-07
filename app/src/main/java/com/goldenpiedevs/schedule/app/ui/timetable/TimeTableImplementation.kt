package com.goldenpiedevs.schedule.app.ui.timetable

import android.support.design.widget.AppBarLayout
import android.support.v4.widget.NestedScrollView
import android.view.View
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoWeekModel
import com.goldenpiedevs.schedule.app.core.dao.timetable.DayModel
import com.goldenpiedevs.schedule.app.core.ext.isFirstWeek
import com.goldenpiedevs.schedule.app.core.utils.AppPreference
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl
import io.realm.Realm
import io.realm.RealmList
import org.threeten.bp.LocalDateTime

class TimeTableImplementation : BasePresenterImpl<TimeTableView>(), TimeTablePresenter {

    companion object {
        const val ANIMATION_DELAY = 1500L
    }

    private lateinit var firstWeekDays: RealmList<DayModel>
    private lateinit var secondWeekDays: RealmList<DayModel>
    private val realm = Realm.getDefaultInstance()

    override fun getData() {

        firstWeekDays = realm.where(DaoWeekModel::class.java)
                .equalTo("weekNumber", 1.toString()).findFirst()!!.days
        secondWeekDays = realm.where(DaoWeekModel::class.java)
                .equalTo("weekNumber", 2.toString()).findFirst()!!.days

        with(view) {
            showWeekData(true, firstWeekDays)
            showWeekData(false, firstWeekDays)
        }
    }

    override fun showCurrentDay() {
        updateCurrentDay()
    }

    private fun updateCurrentDay() {
        val currentDay: Int = (if (isFirstWeek) firstWeekDays else secondWeekDays)
                .let { it.indexOf(getCurrentDayModel(it)) }

        view.showCurrentDay(isFirstWeek, currentDay)
    }

    private fun getCurrentDayModel(collection: RealmList<DayModel>): DayModel =
            collection.find { it.dayNumber == LocalDateTime.now().dayOfWeek.value } ?: DayModel()

    override fun onLessonClicked(id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun scrollToView(appBarLayout: AppBarLayout, scrollView: NestedScrollView, view: View) {
        scrollView.postDelayed({
            appBarLayout.post { appBarLayout.setExpanded(false, true) }
            scrollView.smoothScrollTo(0, (((view.bottom + view.top) / 2)
                    - view.context.resources.getDimensionPixelSize(R.dimen.header_size)))
        }, if (AppPreference.animateScrollToCard) ANIMATION_DELAY else 0)
    }

    override fun detachView() {
        if (!realm.isClosed)
            realm.close()

        super.detachView()
    }
}