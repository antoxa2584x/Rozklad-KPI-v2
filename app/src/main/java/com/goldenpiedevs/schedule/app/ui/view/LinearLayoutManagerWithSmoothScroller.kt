package com.goldenpiedevs.schedule.app.ui.view

import android.content.Context


/**
 * Created by Anton. A on 10.08.2018.
 * Version 1.0
 */
class LinearLayoutManagerWithSmoothScroller(context: Context) : PreCachingLayoutManager(context) {

    override fun smoothScrollToPosition(recyclerView: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State?, position: Int) {
        val smoothScroller = CenterSmoothScroller(recyclerView.context)
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)
    }

    private class CenterSmoothScroller internal constructor(context: Context) : androidx.recyclerview.widget.LinearSmoothScroller(context) {

        override fun calculateDtToFit(viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int): Int {
            return boxStart + (boxEnd - boxStart) / 2 - (viewStart + (viewEnd - viewStart) / 2)
        }
    }
}