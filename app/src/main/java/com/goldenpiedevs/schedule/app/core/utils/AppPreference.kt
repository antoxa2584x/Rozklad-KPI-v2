package com.goldenpiedevs.schedule.app.core.utils

import com.chibatching.kotpref.KotprefModel

object AppPreference : KotprefModel() {
    var isFirstLauch by booleanPref(true)
}