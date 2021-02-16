package com.homecredit.weather.cache

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.homecredit.weather.data.models.City
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class PreferenceHelper @Inject constructor(
    context: Context,
    private val gson: Gson
) {

    companion object {
        private const val PREF_WEATHER_PACKAGE_NAME = "com.homecredit.weather.preferences"
        private const val PREF_WEATHER_FAVORITES = "favorites"
    }

    // TODO: low: Can be joined with assignment
    private val preference: SharedPreferences

    init {
        preference = context.getSharedPreferences(PREF_WEATHER_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    fun getFavorites(): ArrayList<City> {
        val rawFavorites = preference.getString(PREF_WEATHER_FAVORITES, "")
        return if (!rawFavorites.isNullOrBlank()) {
            gson.fromJson(rawFavorites, object: TypeToken<ArrayList<City>>() {}.type)
        } else {
            arrayListOf()
        }
    }

    fun addFavorite(city: City) {
        val favorites = getFavorites().apply { add(city) }
        edit(favorites)
    }

    fun removeFavorite(city: City) {
        val favorites = getFavorites().apply { removeAll { it.id == city.id } }
        edit(favorites)
    }

    fun clearFavorites() = preference.edit().remove(PREF_WEATHER_FAVORITES).apply()

    private fun edit(cities: ArrayList<City>) =
        preference.edit().putString(PREF_WEATHER_FAVORITES, gson.toJson(cities)).apply()

}