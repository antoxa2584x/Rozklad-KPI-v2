package com.goldenpiedevs.schedule.app.ui.view

import android.content.Context
import android.graphics.Point
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.WindowManager

/**
 * Created by Anton. A on 27.10.2018.
 * Version 1.0
 */
abstract class PreCachingLayoutManager(private var context: Context) : LinearLayoutManager(context) {
    override fun getExtraLayoutSpace(state: RecyclerView.State): Int {
        return screenHeight
    }

    private val screenHeight: Int by lazy {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        size.y
    }
}