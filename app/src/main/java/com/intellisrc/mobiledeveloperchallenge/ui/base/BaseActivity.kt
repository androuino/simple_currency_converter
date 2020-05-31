package com.intellisrc.mobiledeveloperchallenge.ui.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.intellisrc.mobiledeveloperchallenge.Service
import com.intellisrc.mobiledeveloperchallenge.di.Injector
import com.intellisrc.mobiledeveloperchallenge.ui.FragmentStateChanger
import com.zhuinden.simplestack.BackstackDelegate

abstract class BaseActivity: AppCompatActivity(), LifecycleOwner {
    lateinit var backstackDelegate: BackstackDelegate
    lateinit var fragmentStateChanger: FragmentStateChanger
    private lateinit var service: Service

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get().inject(this)

        service = Service()
    }
}