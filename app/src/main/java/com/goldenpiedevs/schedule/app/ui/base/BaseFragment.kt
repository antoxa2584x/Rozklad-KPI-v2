package com.goldenpiedevs.schedule.app.ui.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : Fragment(), BaseView {
    abstract fun getFragmentLayout(): Int

    override fun getContext(): Context {
        return this.activity!!
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return inflater.inflate(getFragmentLayout(), container, false)
    }

    override fun showProgreeDialog() {
        if (isAdded) (activity as BaseActivity).showProgreeDialog()

    }

    override fun dismissProgreeDialog() {
        if (isAdded) (activity as BaseActivity).dismissProgreeDialog()
    }
}