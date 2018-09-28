package com.goldenpiedevs.schedule.app.core.injection.component

import com.goldenpiedevs.schedule.app.core.injection.module.ContextModule
import com.goldenpiedevs.schedule.app.core.injection.module.NetworkManagerModule
import com.goldenpiedevs.schedule.app.core.injection.module.NetworkingApiModule
import com.goldenpiedevs.schedule.app.core.injection.module.NetworkingConfigurationModule
import com.goldenpiedevs.schedule.app.ui.base.BaseView
import com.goldenpiedevs.schedule.app.ui.choose.group.ChooseGroupImplementation
import com.goldenpiedevs.schedule.app.ui.lesson.LessonImplementation
import com.goldenpiedevs.schedule.app.ui.main.MainImplementation
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class,
    NetworkingConfigurationModule::class,
    NetworkingApiModule::class,
    NetworkManagerModule::class])
interface PresenterInjector {
    fun inject(launcherImplementation: ChooseGroupImplementation)
    fun inject(mainImplementation: MainImplementation)
    fun inject(lessonImplementation: LessonImplementation)

    @Component.Builder
    interface Builder {
        fun build(): PresenterInjector

        fun networkModule(networkModule: NetworkingConfigurationModule): Builder
        fun contextModule(contextModule: ContextModule): Builder
        fun apiModule(networkingApiModule: NetworkingApiModule): Builder
        fun managerModule(networkManagerModule: NetworkManagerModule): Builder

        @BindsInstance
        fun baseView(baseView: BaseView): Builder
    }
}