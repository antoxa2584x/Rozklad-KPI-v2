package com.goldenpiedevs.schedule.app.ui.fragment.keeper

import androidx.annotation.StringRes
import com.goldenpiedevs.schedule.app.ui.base.BaseView

interface FragmentKeeperView : BaseView {
    fun setTitle(@StringRes int: Int)
    fun setTitle(string: String)
    fun makeFullScreen()
}