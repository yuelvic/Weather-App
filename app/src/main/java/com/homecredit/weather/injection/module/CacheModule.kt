package com.homecredit.weather.injection.module

import com.homecredit.weather.cache.implementation.PreferenceCacheImpl
import com.homecredit.weather.data.repository.PreferenceCache
import dagger.Binds
import dagger.Module

@Module
abstract class CacheModule {

    @Binds
    abstract fun bindPreferenceCache(preferenceCacheImpl: PreferenceCacheImpl): PreferenceCache

}