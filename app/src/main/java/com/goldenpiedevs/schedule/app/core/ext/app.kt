package com.goldenpiedevs.schedule.app.core.ext

import android.support.v7.app.AppCompatActivity
import com.goldenpiedevs.schedule.app.ScheduleApplication

val AppCompatActivity.app: ScheduleApplication
    get() = application as ScheduleApplication