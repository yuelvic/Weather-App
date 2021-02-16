package com.homecredit.weather.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homecredit.weather.data.models.City
import com.homecredit.weather.domain.interactor.AddFavorite
import com.homecredit.weather.domain.interactor.GetFavorites
import com.homecredit.weather.domain.interactor.RemoveFavorite
import com.homecredit.weather.presenter.data.Resource
import com.homecredit.weather.presenter.data.ResourceState
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import javax.inject.Inject

// TODO: high: This also somewhat violates Interface Segregation Principle.
//  `CityListFragment` does not need `addFavorite` or `removeFavorite` so why does it know about it?
//  It seems like this whole ViewModel should be removed and those use cases utilized in
//  `CityListViewModel` and `CityDetailViewModel` where they would be used to combine data with
//  data from the server and publishing models that are tailored specifically for the Fragment
//  where the data would be displayed.
open class PreferenceViewModel @Inject internal constructor(
    private val getFavorites: GetFavorites,
    private val addFavorite: AddFavorite,
    private val removeFavorite: RemoveFavorite
) : ViewModel() {

    // TODO: medium: What if we filtered Resource of type `ResourceState.SUCCESS` already on the
    //  side of ViewModel and provide just `List<City>`? Views discard the `ERROR` state anyway.
    private val favoritesListLiveData: MutableLiveData<Resource<ArrayList<City>>> = MutableLiveData()

    override fun onCleared() {
        getFavorites.dispose()
        // TODO: low: forgot to dispose `addFavorite` and `removeFavorite`?
        super.onCleared()
    }

    fun favoritesListLiveData(): LiveData<Resource<ArrayList<City>>> = favoritesListLiveData

    fun getFavorites() {
        getFavorites.execute(object : DisposableSingleObserver<ArrayList<City>>() {
            // TODO: low: RxJava can never produce a null value.
            override fun onSuccess(result: ArrayList<City>?) {
                favoritesListLiveData.postValue(Resource(ResourceState.SUCCESS,
                    result, null))
            }

            override fun onError(e: Throwable?) {
                favoritesListLiveData.postValue(Resource(ResourceState.ERROR,
                    null, "No favorites found"))
            }
        })
    }

    fun addFavorite(city: City) {
        addFavorite.execute(city).subscribe {
            getFavorites()
        }
    }

    fun removeFavorite(city: City) {
        removeFavorite.execute(city).subscribe {
            getFavorites()
        }
    }

}