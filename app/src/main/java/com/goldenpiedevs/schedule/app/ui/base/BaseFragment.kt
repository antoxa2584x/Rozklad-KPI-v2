package com.goldenpiedevs.schedule.app.ui.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goldenpiedevs.schedule.app.ui.view.hideSoftKeyboard
import java.util.concurrent.atomic.AtomicLong

abstract class BaseFragment : androidx.fragment.app.Fragment(), BaseView {
    companion object {
        const val KEY_FRAGMENT_ID = "KEY_ACTIVITY_ID"
    }

    private var root: View? = null

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

    override fun getIt() = this

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        fragmentId = savedInstanceState?.getLong(BaseFragment.KEY_FRAGMENT_ID) ?: nextId.getAndIncrement()
        root = inflater.inflate(getFragmentLayout(), container, false)
        return root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_FRAGMENT_ID, fragmentId)
    }

    override fun showProgressDialog() {
        if (isAdded) {
            hideSoftKeyboard()
            (activity as BaseActivity<*, *>).showProgressDialog()
        }

    }

    override fun dismissProgressDialog() {
        if (isAdded) (activity as BaseActivity<*, *>).dismissProgressDialog()
    }
}