package com.goldenpiedevs.schedule.app.ui.timetable

import android.content.Intent
import android.support.design.widget.AppBarLayout
import android.support.v4.widget.NestedScrollView
import android.view.View
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoDayModel
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoWeekModel
import com.goldenpiedevs.schedule.app.core.ext.isFirstWeek
import com.goldenpiedevs.schedule.app.core.utils.AppPreference
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl
import com.goldenpiedevs.schedule.app.ui.lesson.LessonActivity
import com.goldenpiedevs.schedule.app.ui.lesson.LessonImplementation
import io.realm.Realm
import io.realm.RealmList
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.temporal.IsoFields
import java.util.*

class TimeTableImplementation : BasePresenterImpl<TimeTableView>(), TimeTablePresenter {
    companion object {
        const val ANIMATION_DELAY = 1500L
    }

    private lateinit var firstWeekDaoDays: RealmList<DaoDayModel>
    private lateinit var secondWeekDaoDays: RealmList<DaoDayModel>
    private val realm = Realm.getDefaultInstance()

    override fun getData() {

        firstWeekDaoDays = DaoWeekModel().getFirstWeekDays(realm)
        secondWeekDaoDays = DaoWeekModel().getSecondWeekDays(realm)

        with(view) {
            showWeekData(true, firstWeekDaoDays)
            showWeekData(false, secondWeekDaoDays)
        }
    }

    override fun showCurrentDay() {
        updateCurrentDay(isFirstWeek, LocalDateTime.now().dayOfWeek.value, false)
    }

    private fun updateCurrentDay(week: Boolean, day: Int, forceDisableAnimDelay: Boolean) {
        val currentDay: Int = (if (week) firstWeekDaoDays else secondWeekDaoDays)
                .let { it.indexOf(getCurrentDayModel(it, day)) }

        view.showCurrentDay(week, currentDay, forceDisableAnimDelay)
    }

    private fun getCurrentDayModel(collection: RealmList<DaoDayModel>, day: Int): DaoDayModel =
            collection.find { it.dayNumber == day } ?: DaoDayModel()

    override fun onLessonClicked(id: Int) {
        with(view.getContext()) {
            startActivity(Intent(this, LessonActivity::class.java)
                    .putExtra(LessonImplementation.LESSON_ID, id))
        }
    }

    override fun scrollToView(appBarLayout: AppBarLayout, scrollView: NestedScrollView, view: View, forceDisableAnimDelay: Boolean) {
        scrollView.postDelayed({
            appBarLayout.post { appBarLayout.setExpanded(false, true) }
            scrollView.smoothScrollTo(0, (((view.bottom + view.top) / 2)
                    - view.context.resources.getDimensionPixelSize(R.dimen.header_size)))
        }, when {
            forceDisableAnimDelay -> 0
            AppPreference.animateScrollToCard -> ANIMATION_DELAY
            else -> 0
        })
    }

    override fun scrollToDay(dateClicked: Date?) {
        val localDate = LocalDateTime.ofEpochSecond(dateClicked!!.time / 1000L, 0, ZoneOffset.UTC).toLocalDate()
        val week = localDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) % 2 == 0
        val day = localDate.dayOfWeek.value

        updateCurrentDay(week, day, true)
    }

    override fun detachView() {
        if (!realm.isClosed)
            realm.close()

        super.detachView()
    }
}