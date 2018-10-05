package com.goldenpiedevs.schedule.app.ui.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.concurrent.atomic.AtomicLong

abstract class BaseFragment : Fragment(), BaseView {
    companion object {
        const val KEY_FRAGMENT_ID = "KEY_ACTIVITY_ID"
    }

    private var nextId = AtomicLong(0)
    private var fragmentId: Long = 0

    abstract fun getFragmentLayout(): Int

    override fun getContext(): Context {
        return this.activity!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        fragmentId = savedInstanceState?.getLong(BaseFragment.KEY_FRAGMENT_ID) ?: nextId.getAndIncrement()
        return view ?: inflater.inflate(getFragmentLayout(), container, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_FRAGMENT_ID, fragmentId)
    }

    override fun showProgressDialog() {
        if (isAdded) (activity as BaseActivity<*, *>).showProgressDialog()

    }

    override fun dismissProgressDialog() {
        if (isAdded) (activity as BaseActivity<*, *>).dismissProgressDialog()
    }
}