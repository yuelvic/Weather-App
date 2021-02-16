package com.homecredit.weather.data.executor

import com.homecredit.weather.domain.executor.ThreadExecutor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

// TODO: medium: Honestly I don't see a point in this and `PostExecutionThread`.
//  They are only used for `subscribeOn` and `observeOn` Rx operators in `SingleUseCase` and
//  `CompletableUseCase`. Why not just use `Scheduler` class directly? `Schedulers.IO` or
//  `Schedulers.Trampoline` would achieve the same goal. Depending on `Scheduler` directly
//  would also simplify Unit testing in cases where timing is important by using
//  `TestScheduler` that can artificially advance time without actually waiting.
//  Furthermore, this is not tested and since it deals with low level threads, you would have
//  hard time writing such tests by yourself.
open class JobExecutor @Inject constructor() : ThreadExecutor {

    // TODO: low: No need to have it as a class member, could be inlined inside constructor.
    private val workQueue: LinkedBlockingQueue<Runnable> = LinkedBlockingQueue()

    private val threadPoolExecutor: ThreadPoolExecutor

    // TODO: low: No need to have it as a class member, could be inlined inside constructor.
    private val threadFactory: ThreadFactory

    init {
        this.threadFactory = JobThreadFactory()
        this.threadPoolExecutor = ThreadPoolExecutor(INITIAL_POOL_SIZE, MAX_POOL_SIZE,
            KEEP_ALIVE_TIME.toLong(), KEEP_ALIVE_TIME_UNIT, this.workQueue, this.threadFactory)
    }

    override fun execute(runnable: Runnable?) {
        requireNotNull(runnable) { "Runnable to execute cannot be null" }
        this.threadPoolExecutor.execute(runnable)
    }

    private class JobThreadFactory : ThreadFactory {
        private var counter = 0

        override fun newThread(runnable: Runnable): Thread {
            return Thread(runnable, THREAD_NAME + counter++)
        }

        companion object {
            private const val THREAD_NAME = "android_"
        }
    }

    companion object {
        private const val INITIAL_POOL_SIZE = 3
        private const val MAX_POOL_SIZE = 5

        // Sets the amount of time an idle thread waits before terminating
        private const val KEEP_ALIVE_TIME = 10

        // Sets the Time Unit to seconds
        private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS
    }

}