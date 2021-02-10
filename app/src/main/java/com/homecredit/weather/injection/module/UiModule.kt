package com.homecredit.weather.injection.module

import com.homecredit.weather.MainActivity
import com.homecredit.weather.UiThread
import com.homecredit.weather.domain.executor.PostExecutionThread
import com.homecredit.weather.screens.CityDetailsFragment
import com.homecredit.weather.screens.CityListFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeCityListFragment(): CityListFragment

    @ContributesAndroidInjector
    abstract fun contributeCityDetailsFragment(): CityDetailsFragment

}