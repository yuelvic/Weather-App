package com.homecredit.weather.domain.interactor

import com.homecredit.weather.data.models.City
import com.homecredit.weather.data.models.WeatherRequest
import com.homecredit.weather.data.repository.WeatherRemote
import com.homecredit.weather.domain.executor.PostExecutionThread
import com.homecredit.weather.domain.executor.ThreadExecutor
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

open class GetCity @Inject constructor(
        private val weatherRemote: WeatherRemote,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
) : SingleUseCase<City, WeatherRequest>(threadExecutor, postExecutionThread) {

    // TODO: medium: You accept nullable `WeatherRequest` but always treat it as if it could never
    //  be null. Would be nice to define params as not nullable and propagate the nullability
    //  issue upstream where the nullability will probably just magically disappear since
    //  you always provide non-null object.
    override fun buildUseCaseObservable(params: WeatherRequest?): Single<City> {
        return weatherRemote.getCity(
                ids = params?.ids!!,
                key = params.key
        )
    }

}