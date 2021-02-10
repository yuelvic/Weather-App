package com.homecredit.weather.injection

import android.app.Application
import com.homecredit.weather.WeatherApplication
import com.homecredit.weather.injection.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    PresentationModule::class,
    ApplicationModule::class,
    RemoteModule::class,
    CacheModule::class,
    DataModule::class,
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