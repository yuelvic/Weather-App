package com.homecredit.weather.remote.service

import com.homecredit.weather.data.models.City
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("data/2.5/group")
    fun getCities(
        @Query("id") ids: String,
        @Query("units") unit: String,
        @Query("appId") key: String
    ): Single<ArrayList<City>>

    @GET("data/2.5/city")
    fun getCity(
        @Query("id") ids: String,
        @Query("units") unit: String,
        @Query("appId") key: String
    ): Single<City>

}