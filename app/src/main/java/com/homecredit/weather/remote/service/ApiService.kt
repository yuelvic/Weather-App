package com.homecredit.weather.remote.service

import com.homecredit.weather.data.models.City
import com.homecredit.weather.data.models.Group
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("data/2.5/group")
    fun getCities(
        @Query("id") ids: String,
        @Query("units") unit: String? = "metric",
        @Query("appId") key: String
    ): Single<Group>

    @GET("data/2.5/weather")
    fun getCity(
        // TODO: low: Better name it just `id` since it only contains a single id.
        @Query("id") ids: String,
        @Query("units") unit: String? = "metric",
        @Query("appId") key: String
    ): Single<City>

}