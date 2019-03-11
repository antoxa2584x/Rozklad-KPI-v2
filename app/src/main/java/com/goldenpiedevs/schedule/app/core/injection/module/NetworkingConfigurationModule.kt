package com.goldenpiedevs.schedule.app.core.injection.module

import android.annotation.SuppressLint
import com.goldenpiedevs.schedule.app.BuildConfig
import com.goldenpiedevs.schedule.app.core.api.utils.CustomHttpLoggingInterceptor
import com.goldenpiedevs.schedule.app.core.api.utils.ToJson
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.X509TrustManager

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
    fun provideOkHttpClient(customHttpLoggingInterceptor: CustomHttpLoggingInterceptor): OkHttpClient {
        return getUnsafeOkHttpClient()
            .addNetworkInterceptor(customHttpLoggingInterceptor)
            .build()
    }

    @SuppressLint("TrustAllX509TrustManager")
    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
        try {
            val trustAllCerts = arrayOf(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier(object : HostnameVerifier {
                @SuppressLint("BadHostnameVerifier")
                override fun verify(hostname: String, session: SSLSession): Boolean {
                    return true
                }
            })
            return builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

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