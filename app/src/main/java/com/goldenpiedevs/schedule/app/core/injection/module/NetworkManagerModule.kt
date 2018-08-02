package com.goldenpiedevs.schedule.app.core.injection.module

import android.content.Context
import com.goldenpiedevs.schedule.app.core.api.group.GroupManager
import com.goldenpiedevs.schedule.app.core.api.group.GroupService
import com.goldenpiedevs.schedule.app.core.api.lessons.LessonsManager
import com.goldenpiedevs.schedule.app.core.api.lessons.LessonsService
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object NetworkManagerModule {
    @Provides
    @Reusable
    @JvmStatic
    fun provideGroupManager(groupService: GroupService) = GroupManager(groupService)

    @Provides
    @Reusable
    @JvmStatic
    fun provideLessonsManager(lessonsService: LessonsService, context: Context) = LessonsManager(lessonsService, context)
}