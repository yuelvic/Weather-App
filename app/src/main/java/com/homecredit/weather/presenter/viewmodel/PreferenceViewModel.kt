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

open class PreferenceViewModel @Inject internal constructor(
    private val getFavorites: GetFavorites,
    private val addFavorite: AddFavorite,
    private val removeFavorite: RemoveFavorite
) : ViewModel() {

    private val favoritesListLiveData: MutableLiveData<Resource<ArrayList<City>>> = MutableLiveData()

    override fun onCleared() {
        getFavorites.dispose()
        super.onCleared()
    }

    fun favoritesListLiveData(): LiveData<Resource<ArrayList<City>>> = favoritesListLiveData

    fun getFavorites() {
        getFavorites.execute(object : DisposableSingleObserver<ArrayList<City>>() {
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