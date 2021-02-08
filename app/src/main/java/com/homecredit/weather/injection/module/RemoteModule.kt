package com.homecredit.weather.injection.module

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.homecredit.weather.BuildConfig
import com.homecredit.weather.remote.ServiceFactory
import com.homecredit.weather.remote.service.ApiService
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        internal fun provideGson(): Gson {
            return GsonBuilder().create()
        }

        @Provides
        @JvmStatic
        internal fun provideChucker(context: Context): ChuckerInterceptor {
            return ChuckerInterceptor.Builder(context).build()
        }

        @Provides
        @JvmStatic
        internal fun provideApiService(chuckerInterceptor: ChuckerInterceptor): ApiService {
            return ServiceFactory.provideApiService(
                clazz = ApiService::class.java,
                interceptors = arrayListOf(chuckerInterceptor),
                isDebug = BuildConfig.DEBUG,
                baseUrl = "http://api.openweathermap.org/"
            )
        }
    }

}