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
        updateCurrentDay()
    }

    private fun updateCurrentDay() {
        val currentDay: Int = (if (isFirstWeek) firstWeekDaoDays else secondWeekDaoDays)
                .let { it.indexOf(getCurrentDayModel(it)) }

        view.showCurrentDay(isFirstWeek, currentDay)
    }

    private fun getCurrentDayModel(collection: RealmList<DaoDayModel>): DaoDayModel =
            collection.find { it.dayNumber == LocalDateTime.now().dayOfWeek.value } ?: DaoDayModel()

    override fun onLessonClicked(id: Int) {
        with(view.getContext()) {
            startActivity(Intent(this, LessonActivity::class.java)
                    .putExtra(LessonImplementation.LESSON_ID, id))
        }
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