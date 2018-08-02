package com.goldenpiedevs.schedule.app.ui.base

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.ext.hideSoftKeyboard
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.indeterminateProgressDialog
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by Anton. A on 13.03.2018.
 * Version 1.0
 */
abstract class BaseActivity : AppCompatActivity(), BaseView {
    companion object {
        const val KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID"
    }

    private var dialog: AlertDialog? = null
    private var nextId = AtomicLong(0)
    private var activityId: Long = 0

    override fun getContext(): Context {
        return this
    }

    abstract fun getActivityLayout(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getActivityLayout())

        activityId = savedInstanceState?.getLong(KEY_ACTIVITY_ID) ?: nextId.getAndIncrement()

        if (findViewById<Toolbar>(R.id.toolbar) != null) {
            setSupportActionBar(toolbar)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putLong(KEY_ACTIVITY_ID, activityId)
    }

    override fun showProgreeDialog() {
        dialog = indeterminateProgressDialog(R.string.loading)
        hideSoftKeyboard()
        dialog!!.show()
    }

    override fun dismissProgreeDialog() {
        dialog!!.dismiss()
    }
}