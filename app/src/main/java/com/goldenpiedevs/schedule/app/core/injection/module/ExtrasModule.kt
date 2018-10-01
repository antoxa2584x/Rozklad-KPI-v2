package com.goldenpiedevs.schedule.app.core.injection.module

import android.content.Context
import com.goldenpiedevs.schedule.app.core.notifications.manger.NotificationManager
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object ExtrasModule {
    @Provides
    @Reusable
    fun provideNotificationManager(context: Context) = NotificationManager(context)
}