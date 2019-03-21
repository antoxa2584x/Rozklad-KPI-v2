package com.goldenpiedevs.schedule.app.ui.fragment.keeper

import android.os.Bundle
import androidx.annotation.StringRes
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ui.base.BaseActivity
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.toolbar.*

class FragmentKeeperActivity : BaseActivity<FragmentKeeperPresenter, FragmentKeeperView>(), FragmentKeeperView {
    lateinit var presenter: FragmentKeeperPresenter

    override fun getPresenterChild() = presenter
    override fun getActivityLayout() = R.layout.fragment_keeper_layout

    override fun setTitle(@StringRes int: Int) {
        setTitle(resources.getString(int))
    }

    override fun setTitle(string: String) {
        toolbar.title = string
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Slidr.attach(this)

        presenter = FragmentKeeperImplementation()

        with(presenter) {
            attachView(this@FragmentKeeperActivity)
            setFragmentManager(supportFragmentManager)
            showFragmentForBundle(intent.extras, savedInstanceState)
        }
    }

    override fun onBackPressed() {
        finish()
    }
}