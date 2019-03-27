package com.goldenpiedevs.schedule.app.core.utils.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.goldenpiedevs.schedule.app.ui.main.MainActivity


/**
 * Created by Anton. A on 23.03.2019.
 * Version 1.0
 */


const val NO_INTERNET = -1
const val RESULT_OK = 200
const val RESULT_FAILED = 500

fun Context.restartApp() {
    val intent = Intent(this, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    this.startActivity(intent)
    if (this is Activity) {
        this.finish()
    }

    Runtime.getRuntime().exit(0)
}


fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}