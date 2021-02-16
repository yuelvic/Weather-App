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

    // Nice that you wrap blocking code within wrapper!
    // TODO: high: You don't actually need to publish a list of Cities, since you care only
    //  about the ids of those cities.
    //  This ripples through `PreferenceCache`, `GetFavorites`, `PreferenceViewModel`,
    //  `CityListFragment` and `CityListAdapter` where it is finally used to filter by ids.
    //  Similarly for `addFavorite` and  `removeFavorite`, they could just accept ids instead
    //  of full `City` objects.
    //  It would also simplify `PreferenceHelper` where you currently depend on rather unsafe
    //  gson where change in the structure of `City` would break deseralization.
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