package com.goldenpiedevs.schedule.app.ui.view

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View


/**
 * Created by Anton. A on 27.10.2018.
 * Version 1.0
 */
open class PreloadLinearLayoutManager : LinearLayoutManager {

    private var mOrientationHelper: OrientationHelper? = null

    /**
     * As [LinearLayoutManager.collectAdjacentPrefetchPositions] will prefetch one view for us,
     * we only need to prefetch additional ones.
     */
    private var mAdditionalAdjacentPrefetchItemCount = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        mOrientationHelper = OrientationHelper.createOrientationHelper(this, orientation)
    }

    fun setPreloadItemCount(preloadItemCount: Int) {
        if (preloadItemCount < 1) {
            throw IllegalArgumentException("adjacentPrefetchItemCount must not smaller than 1!")
        }
        mAdditionalAdjacentPrefetchItemCount = preloadItemCount - 1
    }

    override fun collectAdjacentPrefetchPositions(dx: Int, dy: Int, state: RecyclerView.State?,
                                                  layoutPrefetchRegistry: LayoutPrefetchRegistry?) {
        super.collectAdjacentPrefetchPositions(dx, dy, state, layoutPrefetchRegistry)
        /* We make the simple assumption that the list scrolls down to load more data,
         * so here we ignore the `mShouldReverseLayout` param.
         * Additionally, as we can not access mLayoutState, we have to get related info by ourselves.
         * See LinearLayoutManager#updateLayoutState
         */
        val delta = if (orientation == LinearLayoutManager.HORIZONTAL) dx else dy
        if (childCount == 0 || delta == 0) {
            // can't support this scroll, so don't bother prefetching
            return
        }
        val layoutDirection = if (delta > 0) 1 else -1
        val child = getChildClosest(layoutDirection)
        val currentPosition = getPosition(child!!) + layoutDirection
        val scrollingOffset: Int
        /* Our aim is to pre-load, so we just handle layoutDirection=1 situation.
         * If we handle layoutDirection=-1 situation, each scroll with slightly toggle directions
         * will cause huge numbers of bindings.
         */
        if (layoutDirection == 1) {
            scrollingOffset = mOrientationHelper!!.getDecoratedEnd(child) - mOrientationHelper!!.endAfterPadding
            for (i in currentPosition + 1 until currentPosition + mAdditionalAdjacentPrefetchItemCount + 1) {
                if (i >= 0 && i < state!!.itemCount) {
                    layoutPrefetchRegistry!!.addPosition(i, Math.max(0, scrollingOffset))
                }
            }
        }
    }

    private fun getChildClosest(layoutDirection: Int): View? {
        return getChildAt(if (layoutDirection == -1) 0 else childCount - 1)
    }
}