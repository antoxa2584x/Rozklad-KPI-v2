package com.goldenpiedevs.schedule.app.core.injection.module

import com.goldenpiedevs.schedule.app.core.api.GroupService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

@Module
object NetworkingApiModule {

    @Provides
    @Reusable
    @JvmStatic
    fun provideGroupsService(retrofit: Retrofit) = retrofit.create(GroupService::class.java)

}