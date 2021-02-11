package com.homecredit.weather.data.models

import com.google.gson.annotations.SerializedName

data class Group(
    @SerializedName("list")
    var cities: ArrayList<City>?
)

data class City(
    var id: Long?,
    var name: String?,
    @SerializedName("main")
    var temperature: Temperature?,
    var weather: ArrayList<Weather>?,
    var favorite: Boolean = false
)

data class Temperature(
    var temp: Double?,
    @SerializedName("temp_min")
    var low: Double?,
    @SerializedName("temp_max")
    var high: Double?
)

data class Weather(
    @SerializedName("main")
    var status: String?
)

data class WeatherRequest(
        var ids: String,
        var key: String
)