package com.goldenpiedevs.schedule.app.core.ext

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ScheduleApplication
import com.goldenpiedevs.schedule.app.core.utils.preference.UserPreference
import io.realm.RealmList
import org.threeten.bp.LocalDate
import org.threeten.bp.format.TextStyle
import org.threeten.bp.temporal.IsoFields
import java.util.*

val appLocale = Locale("uk", "UA")

val today: LocalDate = LocalDate.now()
val todayName: String = today.dayOfWeek.getDisplayName(TextStyle.FULL, appLocale)
val todayNumberInWeek = today.dayOfWeek.value.toString()
val currentWeek = (today.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) % 2) xor if (UserPreference.reverseWeek) 1 else 0
val isFirstWeek = currentWeek == 0


val AppCompatActivity.app: ScheduleApplication
    get() = application as ScheduleApplication

val RecyclerView.ViewHolder.context: Context
    get() = itemView.context

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
        lp.height = resources.getDimension(R.dimen.header_size).toInt()
    }
}

fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(adapterPosition, itemViewType)
    }
    return this
}

fun Context.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun Date.getCurrentMonth() = Calendar.getInstance().apply { time = this@getCurrentMonth }.get(Calendar.MONTH)