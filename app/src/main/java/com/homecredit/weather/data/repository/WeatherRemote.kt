package com.homecredit.weather.data.repository

import com.homecredit.weather.data.models.City
import com.homecredit.weather.data.models.Group
import io.reactivex.rxjava3.core.Single

interface WeatherRemote {

    fun getCities(
            ids: String,
            key: String
    ): Single<Group>

    fun getCity(
            ids: String,
            key: String
    ): Single<City>

}