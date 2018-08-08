package com.goldenpiedevs.schedule.app.ui.base

import com.goldenpiedevs.schedule.app.core.injection.component.DaggerPresenterInjector
import com.goldenpiedevs.schedule.app.core.injection.component.PresenterInjector
import com.goldenpiedevs.schedule.app.core.injection.module.ContextModule
import com.goldenpiedevs.schedule.app.core.injection.module.NetworkManagerModule
import com.goldenpiedevs.schedule.app.core.injection.module.NetworkingApiModule
import com.goldenpiedevs.schedule.app.core.injection.module.NetworkingConfigurationModule
import com.goldenpiedevs.schedule.app.ui.choose.group.ChooseGroupImplementation
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

    private lateinit var injector: PresenterInjector

    override fun attachView(view: V) {
        this.view = view

        injector = DaggerPresenterInjector
                .builder()
                .baseView(view)
                .contextModule(ContextModule)
                .networkModule(NetworkingConfigurationModule)
                .apiModule(NetworkingApiModule)
                .managerModule(NetworkManagerModule)
                .build()

        inject()
    }

    override fun detachView() {
        compositeDisposable.dispose()
    }

    private fun inject() {
        when (this) {
            is ChooseGroupImplementation -> injector.inject(this)
            is MainImplementation -> injector.inject(this)
        }
    }
}