package com.intellisrc.mobiledeveloperchallenge

import android.app.Application
import android.content.res.Resources
import android.os.Build
import android.os.CountDownTimer
import android.os.StrictMode
import androidx.annotation.RequiresApi
import com.intellisrc.mobiledeveloperchallenge.di.component.ApplicationComponent
import com.intellisrc.mobiledeveloperchallenge.di.component.DaggerApplicationComponent
import com.intellisrc.mobiledeveloperchallenge.di.modules.AndroidModule
import com.squareup.leakcanary.BuildConfig
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class App : Application() {
    private var applicationComponent: ApplicationComponent? = null
    private var sExecutor: ExecutorService? = null

    init {
        Timber.plant(Timber.DebugTree())
    }

    override fun onCreate() {
        if (BuildConfig.DEBUG) {

            val threadPolicy =
                StrictMode.ThreadPolicy.Builder()
                    .detectCustomSlowCalls()
                    .detectNetwork()
                    .penaltyLog()
            val vmPolicy = StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedRegistrationObjects()
                .detectLeakedClosableObjects()
                .detectActivityLeaks()
                .detectFileUriExposure()
                .penaltyLog()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                sExecutor = Executors.newSingleThreadExecutor()
                //threadPolicy.penaltyListener(sExecutor, OnThreadViolationListener { v -> handleViolation(v) })
                //vmPolicy.penaltyListener(sExecutor, OnVmViolationListener { v -> handleViolation(v) })
            }

            StrictMode.setThreadPolicy(threadPolicy.build())
            StrictMode.setVmPolicy(vmPolicy.build())
        }
        super.onCreate()
        INSTANCE = this
        // leak canary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)

        applicationComponent = DaggerApplicationComponent
            .builder()
            .androidModule(AndroidModule(this))
            .build()
        applicationComponent?.inject(this)

        try {
            object : CountDownTimer(1800000, 1000) {
                override fun onTick(p0: Long) {}

                @RequiresApi(Build.VERSION_CODES.O)
                override fun onFinish() {
                    Timber.tag(TAG).d("Updating the rates")
                    applicationComponent?.mainFragmentViewModel?.getHistoricalData()
                    start()
                }
            }.start()
        } catch (ex: Exception) {
            Timber.tag(TAG).e("Exception in App class->onCreate->CountDownTimer->${ex.message}")
        }
    }

    fun appComponent(): ApplicationComponent? {
        return applicationComponent
    }

    companion object {
        const val TAG = "App"
        private var INSTANCE: App? = null
        fun get(): App? {
            return INSTANCE
        }

        var res: Resources? = null
            private set
    }
}