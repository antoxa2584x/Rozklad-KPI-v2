package com.goldenpiedevs.schedule.app.ui.map

import android.support.annotation.DrawableRes
import com.davemorrissey.labs.subscaleview.ImageSource
import com.goldenpiedevs.schedule.app.ui.base.BaseView

interface MapView : BaseView {
    fun showMap(imageSource: ImageSource)
}