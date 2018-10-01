package com.goldenpiedevs.schedule.app.core.utils

import com.chibatching.kotpref.KotprefModel

object AppPreference : KotprefModel() {
    var isFirstLaunch by booleanPref(true)
    var groupName by stringPref()
    var groupId by intPref()

    var isCalebdarOpen by booleanPref(false)
}