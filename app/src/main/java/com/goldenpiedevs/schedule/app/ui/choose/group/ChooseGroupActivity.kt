package com.goldenpiedevs.schedule.app.ui.choose.group

import android.os.Bundle
import android.view.WindowManager
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.launcher_activity.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class ChooseGroupActivity : BaseActivity<ChooseGroupPresenter, ChooseGroupView>(), ChooseGroupView {

    private lateinit var presenter: ChooseGroupPresenter

    override fun getPresenterChild(): ChooseGroupPresenter = presenter

    override fun getActivityLayout(): Int = R.layout.launcher_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = ChooseGroupImplementation()

        with(presenter) {
            attachView(this@ChooseGroupActivity)
            setAutocompleteTextView(groupNameAutocomplete)
            blurView(bluredBack)
        }

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    override fun onError() {
        alert(R.string.error) {
            yesButton { android.R.string.ok }
        }.show()
    }
}