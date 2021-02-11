package com.homecredit.weather.domain.interactor

import com.homecredit.weather.data.models.City
import com.homecredit.weather.data.repository.PreferenceCache
import com.homecredit.weather.domain.executor.PostExecutionThread
import com.homecredit.weather.domain.executor.ThreadExecutor
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

open class GetFavorites @Inject constructor(
    private val preferenceCache: PreferenceCache,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<ArrayList<City>, Void>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Void?): Single<ArrayList<City>> {
        return preferenceCache.getFavorites()
    }

}