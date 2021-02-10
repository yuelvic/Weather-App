package com.homecredit.weather.domain.interactor

import com.homecredit.weather.data.models.Group
import com.homecredit.weather.data.models.WeatherRequest
import com.homecredit.weather.data.repository.WeatherRemote
import com.homecredit.weather.domain.executor.PostExecutionThread
import com.homecredit.weather.domain.executor.ThreadExecutor
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

open class GetCities @Inject constructor(
        private val weatherRemote: WeatherRemote,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
) : SingleUseCase<Group, WeatherRequest>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: WeatherRequest?): Single<Group> {
        return weatherRemote.getCities(
                ids = params?.ids!!,
                key = params.key
        )
    }

}