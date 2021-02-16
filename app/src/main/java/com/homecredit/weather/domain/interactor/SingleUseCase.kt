package com.homecredit.weather.domain.interactor

import com.homecredit.weather.domain.executor.PostExecutionThread
import com.homecredit.weather.domain.executor.ThreadExecutor
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Abstract class for a UseCase that returns an instance of a [Single].
 */
abstract class SingleUseCase<T, in Params> constructor(
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) {

    private val disposables = CompositeDisposable()

    /**
     * Builds a [Single] which will be used when the current [SingleUseCase] is executed.
     */
    protected abstract fun buildUseCaseObservable(params: Params? = null): Single<T>

    /**
     * Executes the current use case.
     */
    /**
     * TODO: medium:
     *  Here you unnecessarily disconnect the Rx stream chain. Basically you should not need to
     *  call subscribe from another subscribe.
     *  That is happening for example in PreferenceViewModel.addFavorite -> subscribe ->
     *  getFavorites -> subscribe. The two streams now live separately although one is dependent
     *  on the other. Canceling the first won't cancel the other.
     *  The solution would be to do is similarly to what `CompletableUseCase` is doing:
     *  ```fun execute(params: Params?): Completable```
     *  Returning the Completable and leaving the subscription up to the client. That way, you
     *  could combine the two streams using flatMap (or flatMapSingle or similar, didn't try).
     *  Also, you would not have to deal with that ugly `DisposableSingleObserver`.
     */
    open fun execute(singleObserver: DisposableSingleObserver<T>, params: Params? = null) {
        val single = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                // TODO: low: Unnecessary cast?
                .observeOn(postExecutionThread.scheduler) as Single<T>
        addDisposable(single.subscribeWith(singleObserver))
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    fun dispose() {
        if (!disposables.isDisposed) disposables.dispose()
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

}