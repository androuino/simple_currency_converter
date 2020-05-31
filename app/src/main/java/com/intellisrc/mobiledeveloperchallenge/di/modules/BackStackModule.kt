package com.intellisrc.mobiledeveloperchallenge.di.modules

import com.intellisrc.mobiledeveloperchallenge.utils.BackstackHolder
import com.zhuinden.simplestack.Backstack
import dagger.Module
import dagger.Provides

@Module
class BackstackModule {
    @Provides
    fun backstack(backstackHolder: BackstackHolder): Backstack {
        return backstackHolder.getBackstack()
    }
}