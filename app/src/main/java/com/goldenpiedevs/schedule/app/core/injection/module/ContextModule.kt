package com.goldenpiedevs.schedule.app.core.injection.module

import android.content.Context
import com.goldenpiedevs.schedule.app.ScheduleApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ContextModule(private val application: ScheduleApplication) {
    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application
    }
}