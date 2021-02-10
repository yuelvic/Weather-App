package com.homecredit.weather.injection.module

import com.homecredit.weather.data.executor.JobExecutor
import com.homecredit.weather.domain.executor.ThreadExecutor
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor

}