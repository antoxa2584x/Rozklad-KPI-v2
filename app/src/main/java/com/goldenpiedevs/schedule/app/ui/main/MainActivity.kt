package com.goldenpiedevs.schedule.app.ui.main

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.main_activity_layout.*

class MainActivity : BaseActivity<MainPresenter, MainView>(), MainView {

    lateinit var presenter: MainPresenter

    override fun getPresenterChild(): MainPresenter = presenter

    override fun getActivityLayout(): Int = R.layout.main_activity_layout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        presenter = MainImplementation()
        presenter.attachView(this)
        presenter.setSupportFragmentManager(supportFragmentManager)
        presenter.onTimeTableClick()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun toggleToolbarCollapseMode(isCollapsing: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
}