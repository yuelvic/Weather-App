package com.homecredit.weather.injection

import android.app.Application
import com.homecredit.weather.WeatherApplication
import com.homecredit.weather.injection.module.ApplicationModule
import com.homecredit.weather.injection.module.CacheModule
import com.homecredit.weather.injection.module.RemoteModule
import com.homecredit.weather.injection.module.UiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    RemoteModule::class,
    CacheModule::class,
    UiModule::class
])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }

    fun inject(application: WeatherApplication)

}