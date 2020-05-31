package com.intellisrc.mobiledeveloperchallenge.di

import com.intellisrc.mobiledeveloperchallenge.App
import com.intellisrc.mobiledeveloperchallenge.di.component.ApplicationComponent

object Injector {
    fun get(): ApplicationComponent {
        return App.get()?.appComponent()!!
    }
}