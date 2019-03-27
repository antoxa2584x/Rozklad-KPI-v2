package com.goldenpiedevs.schedule.app.ui.view

import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import java.util.*


object ColorHelper {

    private val materialColors = Arrays.asList(
            -0xf44336,
            -0xE91E63,
            -0x9C27B0,
            -0x673AB7,
            -0x3F51B5,
            -0x2196F3,
            -0x03A9F4,
            -0x00BCD4,
            -0x009688,
            -0x4CAF50,
            -0x8BC34A,
            -0xCDDC39,
            -0xFFC107,
            -0xFF9800
    )

    fun getMaterialColor(key: Any): Int {
        return materialColors[Math.abs(key.hashCode()) % materialColors.size]
    }

    fun generateDrawable(materialColor: Int, diameter: Float): Drawable? {
        val oval = ShapeDrawable(OvalShape())
        oval.intrinsicHeight = diameter.toInt()
        oval.intrinsicWidth = diameter.toInt()
        oval.paint.color = materialColor
        return oval
    }
}