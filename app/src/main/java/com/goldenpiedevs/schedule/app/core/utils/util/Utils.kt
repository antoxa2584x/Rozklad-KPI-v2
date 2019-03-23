package com.goldenpiedevs.schedule.app.core.utils.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.goldenpiedevs.schedule.app.ui.main.MainActivity

/**
 * Created by Anton. A on 23.03.2019.
 * Version 1.0
 */

fun Context.restartApp() {
    val intent = Intent(this, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    this.startActivity(intent)
    if (this is Activity) {
        this.finish()
    }

    Runtime.getRuntime().exit(0)
}
