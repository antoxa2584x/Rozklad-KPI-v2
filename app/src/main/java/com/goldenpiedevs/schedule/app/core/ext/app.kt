package com.goldenpiedevs.schedule.app.core.ext

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.goldenpiedevs.schedule.app.ScheduleApplication

val AppCompatActivity.app: ScheduleApplication
    get() = application as ScheduleApplication

fun Activity.hideSoftKeyboard() {
    currentFocus ?: apply {
        val inputMethodManager = getSystemService(Context
                .INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}

fun Activity.getThemeId(): Int {
    try {
        val wrapper = Context::class.java
        val method = wrapper.getMethod("getThemeResId")
        method.isAccessible = true
        return method.invoke(this) as Int
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return 0
}