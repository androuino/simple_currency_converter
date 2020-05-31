package com.intellisrc.mobiledeveloperchallenge.di.modules

import com.intellisrc.mobiledeveloperchallenge.utils.schedulers.BackgroundScheduler
import com.intellisrc.mobiledeveloperchallenge.utils.schedulers.MainThreadScheduler
import com.intellisrc.mobiledeveloperchallenge.utils.schedulers.NetworkScheduler
import com.intellisrc.mobiledeveloperchallenge.utils.schedulers.Scheduler
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class SchedulerModule {
    @Provides
    @Named("NETWORK")
    fun networkScheduler(networkScheduler: NetworkScheduler): Scheduler {
        return networkScheduler
    }

    @Provides
    @Named("BACKGROUND")
    fun backgroundScheduler(backgroundScheduler: BackgroundScheduler): Scheduler {
        return backgroundScheduler
    }

    @Provides
    @Named("MAIN_THREAD")
    fun mainThreadScheduler(mainThreadScheduler: MainThreadScheduler): Scheduler {
        return mainThreadScheduler
    }
}