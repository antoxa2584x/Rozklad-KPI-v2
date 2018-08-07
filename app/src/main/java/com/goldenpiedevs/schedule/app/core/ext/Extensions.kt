package com.goldenpiedevs.schedule.app.core.ext

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatTextView
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ScheduleApplication
import com.goldenpiedevs.schedule.app.ui.view.adapter.TimeTableAdapter
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.TextStyle
import org.threeten.bp.temporal.IsoFields
import java.util.*

val todayName: String = LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale("uk", "UA"))
val currentWeek = LocalDateTime.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) % 2
val isFirstWeek = currentWeek == 0

val AppCompatActivity.app: ScheduleApplication
    get() = application as ScheduleApplication

fun Activity.hideSoftKeyboard() {
    currentFocus?.apply {
        val inputMethodManager = getSystemService(Context
                .INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}

fun AppBarLayout.lockAppBar(locked: Boolean) {
    if (locked) {
        setExpanded(false, true)
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80f, resources.displayMetrics).toInt()
        val lp = layoutParams as CoordinatorLayout.LayoutParams
        lp.height = px
        layoutParams = lp
    } else {
        setExpanded(true, false)
        isActivated = true
        val lp = layoutParams as CoordinatorLayout.LayoutParams
        lp.height = getResources().getDimension(R.dimen.header_size).toInt()
    }
}

fun TimeTableAdapter.ViewHolder.colorIfToday(weekNumber: Int, title: AppCompatTextView) {
    if (weekNumber - 1 != currentWeek)
        return

    if (title.text.toString().toLowerCase() == todayName) {
        title.apply {
            setBackgroundResource(R.color.primary)
            setTextColor(Color.WHITE)
        }
    } else {
        title.apply {
            setBackgroundResource(android.R.color.transparent)
            setTextColor(ContextCompat.getColor(title.context, R.color.primary_text))
        }
    }
}