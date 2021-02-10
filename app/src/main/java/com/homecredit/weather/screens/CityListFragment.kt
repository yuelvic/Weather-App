package com.homecredit.weather.screens

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.homecredit.weather.BaseFragment
import com.homecredit.weather.R
import com.homecredit.weather.constants.CityCode
import com.homecredit.weather.data.models.City
import com.homecredit.weather.presenter.ViewModelFactory
import com.homecredit.weather.presenter.data.ResourceState
import com.homecredit.weather.presenter.viewmodel.WeatherViewModel
import com.homecredit.weather.screens.adapter.CityListAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_city_list.*
import javax.inject.Inject

class CityListFragment : BaseFragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var weatherViewModel: WeatherViewModel

    private val adapter by lazy { CityListAdapter().apply {
        setOnItemClickListener(object : CityListAdapter.OnItemClickListener {
            override fun onItemClick(city: City) {
                view?.findNavController()?.navigate(
                    R.id.action_cityListFragment_to_cityDetailsFragment,
                    bundleOf(CityDetailsFragment.ARG_CITY_ID to city.id)
                )
            }
        })
    } }

    override fun layout(): Int = R.layout.fragment_city_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)
        setup()
    }

    private fun setup() {
        weatherViewModel = ViewModelProvider(this, viewModelFactory)
                .get(WeatherViewModel::class.java).apply {
                    citiesLiveData().observe(viewLifecycleOwner, {
                        when (it.status) {
                            ResourceState.LOADING -> showLoading(true)
                            ResourceState.SUCCESS -> {
                                showLoading(false)
                                showCities(it.data!!)
                            }
                            ResourceState.ERROR -> {
                                showLoading(false)
                                showMessage(it.message!!)
                            }
                        }
                    })
                    getCities(cityIds())
                }

        rvCities.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CityListFragment.adapter
        }

        srlCities.setOnRefreshListener { weatherViewModel.getCities(cityIds()) }
    }

    private fun showLoading(isRefreshing: Boolean) {
        srlCities.isRefreshing = isRefreshing
    }

    private fun showCities(cities: ArrayList<City>) = adapter.addCities(cities)

    private fun cityIds(): String = "${CityCode.MANILA},${CityCode.PRAGUE},${CityCode.SEOUL}"

}