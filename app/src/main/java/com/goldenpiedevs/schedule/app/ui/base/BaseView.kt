package com.goldenpiedevs.schedule.app.ui.base

import android.content.Context

/**
 * Created by Anton. A on 13.03.2018.
 * Version 1.0
 */
interface BaseView {
    fun getContext(): Context
    fun showProgreeDialog()
    fun dismissProgreeDialog()
}