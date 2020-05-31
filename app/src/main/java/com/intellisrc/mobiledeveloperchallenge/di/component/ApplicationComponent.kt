package com.intellisrc.mobiledeveloperchallenge.di.component

import android.app.Application
import android.content.Context
import com.intellisrc.mobiledeveloperchallenge.App
import com.intellisrc.mobiledeveloperchallenge.Service
import com.intellisrc.mobiledeveloperchallenge.di.ApplicationContext
import com.intellisrc.mobiledeveloperchallenge.di.modules.AndroidModule
import com.intellisrc.mobiledeveloperchallenge.di.modules.BackstackModule
import com.intellisrc.mobiledeveloperchallenge.di.modules.FragmentModule
import com.intellisrc.mobiledeveloperchallenge.di.modules.SchedulerModule
import com.intellisrc.mobiledeveloperchallenge.ui.base.BaseActivity
import com.intellisrc.mobiledeveloperchallenge.ui.base.BaseViewModel
import com.intellisrc.mobiledeveloperchallenge.ui.main.MainFragmentViewModel
import com.intellisrc.mobiledeveloperchallenge.ui.main.repo.CurrencyLayerImplRepo
import com.intellisrc.mobiledeveloperchallenge.utils.BackstackHolder
import com.zhuinden.simplestack.Backstack
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidModule::class,
    FragmentModule::class,
    BackstackModule::class,
    SchedulerModule::class,
    AndroidSupportInjectionModule::class
])
interface ApplicationComponent {
    @get:ApplicationContext
    val context: Context
    val application: Application
    var backstack: Backstack
    var backstackHolder: BackstackHolder

    // application
    fun inject(app: App)
    // service
    fun inject(service: Service)
    // activities
    fun inject(baseActivity: BaseActivity)
    // viewmodel
    fun inject(baseViewModel: BaseViewModel)
    val mainFragmentViewModel: MainFragmentViewModel
    // repository implementations
    fun inject(currencyLayerImplRepo: CurrencyLayerImplRepo)
}