package com.goldenpiedevs.schedule.app.ui.fragment.keeper

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.goldenpiedevs.schedule.app.ui.base.BasePresenter

interface FragmentKeeperPresenter : BasePresenter<FragmentKeeperView> {
    fun showFragmentForBundle(bundle: Bundle?, savedInstanceState: Bundle?)
    fun setFragmentManager(fragmentManager: androidx.fragment.app.FragmentManager)
}