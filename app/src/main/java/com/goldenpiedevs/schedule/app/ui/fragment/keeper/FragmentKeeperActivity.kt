package com.goldenpiedevs.schedule.app.ui.fragment.keeper

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ui.base.BaseActivity
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.fragment_keeper_layout.*
import kotlinx.android.synthetic.main.toolbar.*

class FragmentKeeperActivity : BaseActivity<FragmentKeeperPresenter, FragmentKeeperView>(), FragmentKeeperView {
    lateinit var presenter: FragmentKeeperPresenter

    override fun getPresenterChild() = presenter
    override fun getActivityLayout() = R.layout.fragment_keeper_layout

    private var fullScreen = false
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

    override fun makeFullScreen() {
        fullScreen = true

        fragment_keeper_activity_background.setBackgroundColor(Color.BLACK)
    }


    private fun hideSystemUI() {
        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }

    private fun showSystemUI() {
        supportActionBar?.show()

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    override fun onBackPressed() {
        finish()
    }
}