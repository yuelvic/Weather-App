package com.homecredit.weather.domain.interactor

import com.homecredit.weather.data.models.City
import com.homecredit.weather.data.repository.PreferenceCache
import com.homecredit.weather.domain.executor.PostExecutionThread
import com.homecredit.weather.domain.executor.ThreadExecutor
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

open class AddFavorite @Inject constructor(
    private val preferenceCache: PreferenceCache,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<City>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: City?): Completable {
        return preferenceCache.addFavorite(params!!)
    }

}