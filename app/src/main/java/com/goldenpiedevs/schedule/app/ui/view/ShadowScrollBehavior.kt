package com.goldenpiedevs.schedule.app.ui.view

import android.content.Context
import android.support.annotation.NonNull
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.transition.TransitionManager
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup


/**
 * Created by Anton. A on 14.10.2018.
 * Version 1.0
 */
class ShadowScrollBehavior(context: Context, attrs: AttributeSet) : AppBarLayout.ScrollingViewBehavior(context, attrs), View.OnLayoutChangeListener {

    private var totalDy = 0
    private var isElevated: Boolean = false
    private lateinit var child: View

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View,
                                 dependency: View): Boolean {
        parent.addOnLayoutChangeListener(this)
        this.child = child
        return super.layoutDependsOn(parent, child, dependency)
    }

    override fun onStartNestedScroll(@NonNull coordinatorLayout: CoordinatorLayout,
                                     @NonNull child: View, @NonNull directTargetChild: View,
                                     @NonNull target: View, axes: Int, type: Int): Boolean {
        // Ensure we react to vertical scrolling
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild,
                target, axes, type)
    }

    override fun onNestedPreScroll(@NonNull coordinatorLayout: CoordinatorLayout,
                                   @NonNull child: View, @NonNull target: View,
                                   dx: Int, dy: Int, @NonNull consumed: IntArray, type: Int) {
        totalDy += dy
        if (totalDy <= 0) {
            if (isElevated) {
                val parent = child.parent as ViewGroup
                if (parent != null) {
                    TransitionManager.beginDelayedTransition(parent)
                    ViewCompat.setElevation(child, 0f)
                }
            }
            totalDy = 0
            isElevated = false
        } else {
            if (!isElevated) {
                val parent = child.parent as ViewGroup
                if (parent != null) {
                    TransitionManager.beginDelayedTransition(parent)
                    ViewCompat.setElevation(child, dp2px(child.context, 4))
                }
            }
            if (totalDy > target.bottom)
                totalDy = target.bottom
            isElevated = true
        }
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }


    private fun dp2px(context: Context, dp: Int): Float {
        val r = context.resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics)
    }


    override fun onLayoutChange(view: View, i: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int, i7: Int) {
        totalDy = 0
        isElevated = false
        ViewCompat.setElevation(child, 0f)
    }
}