package com.homecredit.weather.screens

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.homecredit.weather.BaseFragment
import com.homecredit.weather.R
import com.homecredit.weather.data.models.City
import com.homecredit.weather.presenter.ViewModelFactory
import com.homecredit.weather.presenter.data.ResourceState
import com.homecredit.weather.presenter.viewmodel.PreferenceViewModel
import com.homecredit.weather.presenter.viewmodel.WeatherViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_city_details.*
import javax.inject.Inject

class CityDetailsFragment : BaseFragment() {

    companion object {
        const val TAG = "CityDetailsFragment"
        const val ARG_CITY_ID = "cityId"
    }

    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var preferenceViewModel: PreferenceViewModel

    private var city: City? = null
    private var isFavorite = false

    override fun layout(): Int = R.layout.fragment_city_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)
        setup()
    }

    private fun setup() {
        weatherViewModel = ViewModelProvider(this, viewModelFactory)
            .get(WeatherViewModel::class.java).apply {
                cityLiveData().observe(viewLifecycleOwner, {
                    when (it.status) {
                        ResourceState.LOADING -> showLoading(true)
                        ResourceState.ERROR -> {
                            showLoading(false)
                            showMessage(it.message!!)
                        }
                        ResourceState.SUCCESS -> {
                            showLoading(false)
                            showDetails(it.data!!)

                        }
                    }
                })
                getCity(arguments?.getLong(ARG_CITY_ID)!!)
            }

        preferenceViewModel = ViewModelProvider(this, viewModelFactory)
            .get(PreferenceViewModel::class.java).apply {
                favoritesListLiveData().observe(viewLifecycleOwner, {
                    if (it.status == ResourceState.SUCCESS) {
                        isFavorite = it.data!!.count { data -> data.id == city!!.id } > 0
                        ivFavorite.setImageResource(
                            if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_not_favorite)
                    }
                })
            }

        srlCityDetails.setOnRefreshListener {
            weatherViewModel.getCity(arguments?.getLong(ARG_CITY_ID)!!)
        }

        ivFavorite.setOnClickListener {
            if (isFavorite) preferenceViewModel.removeFavorite(city!!)
            else preferenceViewModel.addFavorite(city!!)
        }
    }

    private fun showLoading(isRefreshing: Boolean) {
        srlCityDetails.isRefreshing = isRefreshing
    }

    private fun showDetails(city: City) {
        this.city = city

        tvCity.text = city.name
        tvTemp.text = "${city.temperature?.temp}\u2103"
        tvDescription.text = city.weather?.get(0)!!.status
        tvRange.text = "High ${city.temperature?.high?.toInt()}℃ / " +
                "Low ${city.temperature?.low?.toInt()}℃"

        preferenceViewModel.getFavorites()
    }

}