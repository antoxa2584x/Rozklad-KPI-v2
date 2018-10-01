package com.goldenpiedevs.schedule.app.core.injection.module

import com.goldenpiedevs.schedule.app.core.api.group.GroupManager
import com.goldenpiedevs.schedule.app.core.api.group.GroupService
import com.goldenpiedevs.schedule.app.core.api.lessons.LessonsManager
import com.goldenpiedevs.schedule.app.core.api.lessons.LessonsService
import com.goldenpiedevs.schedule.app.core.notifications.manger.NotificationManager
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object NetworkManagerModule {
    @Provides
    @Reusable
    fun provideGroupManager(groupService: GroupService) = GroupManager(groupService)

    @Provides
    @Reusable
    fun provideLessonsManager(lessonsService: LessonsService, groupManager: GroupManager,
                              notificationManager: NotificationManager) = LessonsManager(lessonsService, groupManager, notificationManager)
}