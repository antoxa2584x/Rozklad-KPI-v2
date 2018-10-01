package com.goldenpiedevs.schedule.app.core.injection.component

import com.goldenpiedevs.schedule.app.core.injection.module.*
import com.goldenpiedevs.schedule.app.core.notifications.work.ShowNotificationWork
import com.goldenpiedevs.schedule.app.ui.choose.group.ChooseGroupImplementation
import com.goldenpiedevs.schedule.app.ui.lesson.LessonImplementation
import com.goldenpiedevs.schedule.app.ui.main.MainImplementation
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class,
    NetworkingConfigurationModule::class,
    NetworkingApiModule::class,
    NetworkManagerModule::class,
    ExtrasModule::class])
interface AppComponent {
    fun inject(launcherImplementation: ChooseGroupImplementation)
    fun inject(mainImplementation: MainImplementation)
    fun inject(lessonImplementation: LessonImplementation)
    fun inject(showNotificationWork: ShowNotificationWork)
}