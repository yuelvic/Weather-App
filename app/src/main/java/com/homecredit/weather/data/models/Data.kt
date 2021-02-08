package com.homecredit.weather.data.models

import com.google.gson.annotations.SerializedName

data class City(
    var id: Long?,
    var name: String?,
    var temperature: Temperature?,
    var weather: ArrayList<Weather>?
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