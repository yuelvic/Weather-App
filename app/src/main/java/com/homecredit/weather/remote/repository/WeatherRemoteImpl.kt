package com.homecredit.weather.remote.repository

import com.homecredit.weather.data.models.City
import com.homecredit.weather.data.models.Group
import com.homecredit.weather.data.repository.WeatherRemote
import com.homecredit.weather.remote.service.ApiService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class WeatherRemoteImpl @Inject constructor(private val apiService: ApiService) : WeatherRemote {

    // TODO: medium: Should accept a List<Int> of ids and `joinToString(",")` them here.
    override fun getCities(ids: String, key: String): Single<Group> {
        return apiService.getCities(
            ids = ids,
            key = key
        )
    }

    override fun getCity(ids: String, key: String): Single<City> {
        return apiService.getCity(
            ids = ids,
            key = key
        )
    }

}