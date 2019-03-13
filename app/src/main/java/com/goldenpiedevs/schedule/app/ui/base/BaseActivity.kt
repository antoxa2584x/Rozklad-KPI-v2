package com.goldenpiedevs.schedule.app.ui.base

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.ext.getStatusBarHeight
import com.goldenpiedevs.schedule.app.ui.main.MainActivity
import com.goldenpiedevs.schedule.app.ui.view.hideSoftKeyboard
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.indeterminateProgressDialog
import java.util.concurrent.atomic.AtomicLong


/**
 * Created by Anton. A on 13.03.2018.
 * Version 1.0
 */
abstract class BaseActivity<T : BasePresenter<V>, V : BaseView> : AppCompatActivity(), BaseView {
    companion object {
        const val KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID"
    }

    private var dialog: AlertDialog? = null
    private var nextId = AtomicLong(0)
    private var activityId: Long = 0

    protected abstract fun getPresenterChild(): T

    override fun getContext(): Context {
        return this
    }

    abstract fun getActivityLayout(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        try {
//            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
//        } catch (e: IllegalStateException) {
//        }

        if (getActivityLayout() == -1)
            return

        setContentView(getActivityLayout())
        activityId = savedInstanceState?.getLong(KEY_ACTIVITY_ID) ?: nextId.getAndIncrement()

        toolbar?.let {
            setSupportActionBar(it)

            if (this@BaseActivity !is MainActivity)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    it.setPadding(0, getStatusBarHeight(), 0, 0)
        }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    override fun onResume() {
        getPresenterChild().onResume()
        super.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_ACTIVITY_ID, activityId)
    }

    override fun showProgressDialog() {
        hideSoftKeyboard()

        dialog = indeterminateProgressDialog(R.string.loading)
    }

    override fun dismissProgressDialog() {
        dialog?.dismiss()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        getPresenterChild().detachView()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }
}