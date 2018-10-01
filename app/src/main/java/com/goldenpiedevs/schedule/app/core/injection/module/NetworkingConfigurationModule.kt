package com.goldenpiedevs.schedule.app.core.injection.module

import com.goldenpiedevs.schedule.app.BuildConfig
import com.goldenpiedevs.schedule.app.core.api.utils.CustomHttpLoggingInterceptor
import com.goldenpiedevs.schedule.app.core.api.utils.ToJson
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import javax.inject.Singleton

@Module
object NetworkingConfigurationModule {
    @Provides
    @Reusable
    fun provideGson(): Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    @Provides
    @Reusable
    fun provideHttpLoggingInterceptor() = CustomHttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) CustomHttpLoggingInterceptor.Level.BODY else CustomHttpLoggingInterceptor.Level.NONE
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(customHttpLoggingInterceptor: CustomHttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(customHttpLoggingInterceptor)
            .build()

    @Provides
    @Reusable
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(object : Converter.Factory() {
                override fun stringConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<*, String>? {
                    if (!hasToJson(annotations)) {
                        return super.stringConverter(type, annotations, retrofit)
                    }
                    return Converter<Any, String> { gson.toJson(it, type) }
                }

                private fun hasToJson(annotations: Array<Annotation>): Boolean {
                    for (annotation in annotations) {
                        if (annotation is ToJson) {
                            return true
                        }
                    }
                    return false
                }
            })
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BuildConfig.HOST)
            .client(okHttpClient)
            .build()
}