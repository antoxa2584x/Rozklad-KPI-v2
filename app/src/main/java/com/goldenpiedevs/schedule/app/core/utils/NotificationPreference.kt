package com.goldenpiedevs.schedule.app.core.utils

import com.chibatching.kotpref.KotprefModel

object NotificationPreference : KotprefModel() {
    var notificationDelay by intPref(15)
    var showNotification by booleanPref(true)
}