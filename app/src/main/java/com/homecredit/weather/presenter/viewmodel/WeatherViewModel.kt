package com.homecredit.weather.presenter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homecredit.weather.BuildConfig
import com.homecredit.weather.data.models.City
import com.homecredit.weather.data.models.Group
import com.homecredit.weather.data.models.WeatherRequest
import com.homecredit.weather.domain.interactor.GetCities
import com.homecredit.weather.domain.interactor.GetCity
import com.homecredit.weather.presenter.data.Resource
import com.homecredit.weather.presenter.data.ResourceState
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import javax.inject.Inject

open class WeatherViewModel @Inject internal constructor(
        private val getCities: GetCities,
        private val getCity: GetCity
) : ViewModel() {

    private val citiesLiveData: MutableLiveData<Resource<ArrayList<City>>> = MutableLiveData()
    private val cityLiveData: MutableLiveData<Resource<City>> = MutableLiveData()

    override fun onCleared() {
        getCities.dispose()
        getCity.dispose()
        super.onCleared()
    }

    fun citiesLiveData() = citiesLiveData

    fun cityLiveData() = cityLiveData

    fun getCities(cityIds: String) {
        citiesLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        getCities.execute(object : DisposableSingleObserver<Group>() {
            override fun onSuccess(result: Group?) {
                citiesLiveData.postValue(Resource(ResourceState.SUCCESS,
                    result?.cities, null))
            }

            override fun onError(e: Throwable?) {
                citiesLiveData.postValue(Resource(ResourceState.ERROR,
                        null, "Failed to fetch weather data"))
            }
        }, WeatherRequest(
                ids = cityIds,
                key = BuildConfig.API_KEY
        ))
    }

    fun getCity(cityId: Long) {
        cityLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        getCity.execute(object : DisposableSingleObserver<City>() {
            override fun onSuccess(result: City?) {
                cityLiveData.postValue(Resource(ResourceState.SUCCESS, result, null))
            }

            override fun onError(e: Throwable?) {
                cityLiveData.postValue(Resource(ResourceState.ERROR,
                        null, "Failed to fetch weather data"))
            }
        }, WeatherRequest(
                ids = cityId.toString(),
                key = BuildConfig.API_KEY
        ))
    }

}