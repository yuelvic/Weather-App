package com.homecredit.weather.data.repository

import com.homecredit.weather.data.models.City
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface PreferenceCache {

    fun getFavorites(): Single<ArrayList<City>>

    fun addFavorite(city: City): Completable

    fun removeFavorite(city: City): Completable

    fun clearFavorites(): Completable

}