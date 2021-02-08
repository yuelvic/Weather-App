package com.homecredit.weather.remote

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceFactory {

    fun <T> provideApiService(clazz: Class<T>, interceptors: List<Interceptor>,
                              isDebug: Boolean, baseUrl: String): T {
        return provideRetrofit(
            clazz = clazz,
            okHttpClient = provideOkHttpClient(
                httpLoggingInterceptor = provideLoggingInterceptor(isDebug),
                interceptors = interceptors
            ),
            baseUrl = baseUrl
        )
    }

    private fun <T> provideRetrofit(clazz: Class<T>,
                                    okHttpClient: OkHttpClient, baseUrl: String): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        return retrofit.create(clazz)
    }

    private fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor,
                                    interceptors: List<Interceptor>): OkHttpClient {
        return OkHttpClient.Builder().apply {
            interceptors.iterator().forEach {
                addInterceptor(it)
            }
            addInterceptor(httpLoggingInterceptor)
            connectTimeout(120, TimeUnit.SECONDS)
            readTimeout(120, TimeUnit.SECONDS)
        }.build()
    }

    private fun provideLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return logging
    }



}