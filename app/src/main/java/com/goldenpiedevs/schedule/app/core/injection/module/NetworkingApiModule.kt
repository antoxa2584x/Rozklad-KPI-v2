package com.goldenpiedevs.schedule.app.core.injection.module

import com.goldenpiedevs.schedule.app.core.api.group.GroupService
import com.goldenpiedevs.schedule.app.core.api.lessons.LessonsService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

@Module
object NetworkingApiModule {

    @Provides
    @Reusable
    fun provideGroupsService(retrofit: Retrofit) = retrofit.create(GroupService::class.java)!!

    @Provides
    @Reusable
    fun provideLessonsService(retrofit: Retrofit) = retrofit.create(LessonsService::class.java)!!

}