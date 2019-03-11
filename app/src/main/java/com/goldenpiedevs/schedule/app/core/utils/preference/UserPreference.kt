package com.goldenpiedevs.schedule.app.core.utils.preference

import com.chibatching.kotpref.KotprefModel
import com.goldenpiedevs.schedule.app.R

object UserPreference : KotprefModel() {
    override val kotprefName: String = context.getString(R.string.user_preference_file_name)

    var notificationDelay by stringPref("15", context.getString(R.string.user_preference_notification_delay_key))
    var showNotification by booleanPref(true, context.getString(R.string.user_preference_show_notification_key))
    var reverseWeek by booleanPref(false, context.getString(R.string.user_preference_reverse_week_key))
}