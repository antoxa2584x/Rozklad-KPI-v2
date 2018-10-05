package com.goldenpiedevs.schedule.app.core.utils

import com.chibatching.kotpref.KotprefModel
import com.goldenpiedevs.schedule.app.R

object NotificationPreference : KotprefModel() {
    override val kotprefName: String = context.getString(R.string.notification_preference_file_name)

    var notificationDelay by stringPref("15", context.getString(R.string.notification_preference_notification_delay_key))
    var showNotification by booleanPref(true, context.getString(R.string.notification_preference_show_notification_key))
}