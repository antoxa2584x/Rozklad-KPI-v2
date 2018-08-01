package com.goldenpiedevs.schedule.app.ui.launcher

import android.os.Bundle
import android.view.WindowManager
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.launcher_activity.*

class LauncherActivity : BaseActivity(), LauncherView {
    private lateinit var presenter: LauncherPresenter

    override fun getActivityLayout(): Int = R.layout.launcher_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = LauncherImplementation()
        presenter.attachView(this)
        presenter.setAutocompleteTextView(group_name_autocomplete)
        presenter.showNextScreen()
        presenter.blurView(blured_back)
    }

    override fun showGroupChooserView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}