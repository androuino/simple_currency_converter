package com.intellisrc.mobiledeveloperchallenge.ui.main

import com.google.auto.value.AutoValue
import com.intellisrc.mobiledeveloperchallenge.Constants
import com.intellisrc.mobiledeveloperchallenge.di.Injector
import com.intellisrc.mobiledeveloperchallenge.ui.base.BaseFragment
import com.intellisrc.mobiledeveloperchallenge.ui.base.BaseKey

@AutoValue
abstract class MainFragmentKey : BaseKey<MainFragmentViewModel>() {
    companion object {
        val create: MainFragmentKey = AutoValue_MainFragmentKey(Constants.MAIN_FRAGMENT)
    }

    override fun shouldShowUp(): Boolean {
        return false
    }

    override fun newViewModel(): MainFragmentViewModel {
        return Injector.get().mainFragmentViewModel
    }

    override fun createFragment(): BaseFragment<MainFragmentViewModel> {
        return MainFragment()
    }
}