package com.goldenpiedevs.schedule.app.ui.launcher

import android.os.Bundle
import android.view.WindowManager
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ui.base.BaseActivity
import com.goldenpiedevs.schedule.app.ui.base.BasePresenter
import com.goldenpiedevs.schedule.app.ui.base.BaseView
import kotlinx.android.synthetic.main.launcher_activity.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class LauncherActivity : BaseActivity(), LauncherView {
    override fun <T : BasePresenter<V>, V : BaseView> getPresenter(): T = presenter as T

    private lateinit var presenter: LauncherPresenter

    override fun getActivityLayout(): Int = R.layout.launcher_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = LauncherImplementation()
        presenter.attachView(this)

        presenter.setAutocompleteTextView(groupNameAutocomplete)
        presenter.showNextScreen()
        presenter.blurView(bluredBack)
    }

    override fun showGroupChooserView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    override fun onError() {
        alert(R.string.error) {
            yesButton { android.R.string.ok }
        }.show()
    }
}