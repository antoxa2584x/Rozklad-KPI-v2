package com.goldenpiedevs.schedule.app.core.injection.module

import com.goldenpiedevs.schedule.app.core.api.GroupService
import com.goldenpiedevs.schedule.app.core.api.group.GroupManager
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object NetworkManagerModule {
    @Provides
    @Reusable
    @JvmStatic
    fun provideGroupManager(groupService: GroupService) = GroupManager(groupService)
}