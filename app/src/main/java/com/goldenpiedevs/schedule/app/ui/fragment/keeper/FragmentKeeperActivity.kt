package com.goldenpiedevs.schedule.app.ui.fragment.keeper

import android.os.Bundle
import android.support.annotation.StringRes
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.toolbar.*

class FragmentKeeperActivity : BaseActivity<FragmentKeeperPresenter, FragmentKeeperView>(), FragmentKeeperView {

    lateinit var presenter: FragmentKeeperPresenter

    override fun getPresenterChild() = presenter
    override fun getActivityLayout() = R.layout.fragment_keeper_layout

    override fun setTitle(@StringRes int: Int) {
        toolbar.setTitle(int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = FragmentKeeperImplementation()
        with(presenter){
            setFragmentManager(supportFragmentManager)
            showFragmentForBundle(intent.extras)
        }
    }
}