package com.goldenpiedevs.schedule.app.ui.fragment.keeper

import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.goldenpiedevs.schedule.app.ui.base.BasePresenter

interface FragmentKeeperPresenter : BasePresenter<FragmentKeeperView> {
    fun showFragmentForBundle(bundle: Bundle?, savedInstanceState: Bundle?)
    fun setFragmentManager(fragmentManager: FragmentManager)
}