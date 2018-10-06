package com.goldenpiedevs.schedule.app.ui.fragment.keeper

import android.support.annotation.StringRes
import com.goldenpiedevs.schedule.app.ui.base.BaseView

interface FragmentKeeperView : BaseView {
    fun setTitle(@StringRes int: Int)
    fun setTitle(string: String)
}