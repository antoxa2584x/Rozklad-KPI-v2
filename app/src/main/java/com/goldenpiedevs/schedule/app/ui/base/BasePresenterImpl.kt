package com.goldenpiedevs.schedule.app.ui.base

import com.goldenpiedevs.schedule.app.ScheduleApplication
import com.goldenpiedevs.schedule.app.core.injection.component.AppComponent
import com.goldenpiedevs.schedule.app.ui.choose.group.ChooseGroupImplementation
import com.goldenpiedevs.schedule.app.ui.lesson.LessonImplementation
import com.goldenpiedevs.schedule.app.ui.main.MainImplementation
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by Anton. A on 13.03.2018.
 * Version 1.0
 */
open class BasePresenterImpl<V : BaseView> : BasePresenter<V> {
    protected val TAG = javaClass.name

    protected lateinit var view: V
    protected val compositeDisposable = CompositeDisposable()

    private lateinit var injector: AppComponent

    override fun attachView(view: V) {
        this.view = view

        injector = (view.getContext().applicationContext as ScheduleApplication).appComponent

        inject()
    }

    override fun detachView() {
        compositeDisposable.dispose()
    }

    override fun onResume() {}

    private fun inject() {
        when (this) {
            is ChooseGroupImplementation -> injector.inject(this)
            is MainImplementation -> injector.inject(this)
            is LessonImplementation -> injector.inject(this)
        }
    }
}