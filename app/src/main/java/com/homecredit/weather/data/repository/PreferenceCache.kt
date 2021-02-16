package com.homecredit.weather.data.repository

import com.homecredit.weather.data.models.City
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface PreferenceCache {

    // TODO: medium: Should use Kotlin immutable `List` collection instead of Java mutable `ArrayList`. Everywhere in the project.
    fun getFavorites(): Single<ArrayList<City>>

    fun addFavorite(city: City): Completable

    fun removeFavorite(city: City): Completable

    // TODO: low: Not used anywhere. YAGNI.
    fun clearFavorites(): Completable

}