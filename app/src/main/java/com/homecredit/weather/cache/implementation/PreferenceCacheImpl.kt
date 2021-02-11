package com.homecredit.weather.cache.implementation

import com.homecredit.weather.cache.PreferenceHelper
import com.homecredit.weather.data.models.City
import com.homecredit.weather.data.repository.PreferenceCache
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class PreferenceCacheImpl @Inject constructor(
    private val preferenceHelper: PreferenceHelper
): PreferenceCache {

    override fun getFavorites(): Single<ArrayList<City>> = Single.defer {
        Single.just(preferenceHelper.getFavorites())
    }

    override fun addFavorite(city: City): Completable = Completable.defer {
        preferenceHelper.addFavorite(city)
        Completable.complete()
    }

    override fun removeFavorite(city: City): Completable = Completable.defer {
        preferenceHelper.removeFavorite(city)
        Completable.complete()
    }

    override fun clearFavorites(): Completable = Completable.defer {
        preferenceHelper.clearFavorites()
        Completable.complete()
    }

}