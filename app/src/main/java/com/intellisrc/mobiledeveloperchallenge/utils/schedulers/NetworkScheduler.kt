package com.intellisrc.mobiledeveloperchallenge.utils.schedulers

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkScheduler @Inject internal constructor() : Scheduler {
    private val executor: Executor =
        Executors.newSingleThreadExecutor()

    override fun execute(runnable: Runnable?) {
        executor.execute(runnable)
    }
}