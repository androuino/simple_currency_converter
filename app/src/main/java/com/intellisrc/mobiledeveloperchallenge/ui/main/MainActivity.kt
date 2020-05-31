package com.intellisrc.mobiledeveloperchallenge.ui.main

import android.os.Bundle
import butterknife.ButterKnife
import com.intellisrc.mobiledeveloperchallenge.R
import com.intellisrc.mobiledeveloperchallenge.di.Injector
import com.intellisrc.mobiledeveloperchallenge.ui.FragmentStateChanger
import com.intellisrc.mobiledeveloperchallenge.ui.base.BaseActivity
import com.intellisrc.mobiledeveloperchallenge.ui.base.BaseKey
import com.intellisrc.mobiledeveloperchallenge.utils.BackstackHolder
import com.intellisrc.mobiledeveloperchallenge.utils.ServiceProvider
import com.zhuinden.simplestack.BackstackDelegate
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestack.StateChanger

class MainActivity : BaseActivity(), StateChanger {

    override fun onCreate(savedInstanceState: Bundle?) {
        backstackDelegate = BackstackDelegate()
        backstackDelegate.setScopedServices(this, ServiceProvider())
        backstackDelegate.onCreate(savedInstanceState, lastCustomNonConfigurationInstance, History.single(MainFragmentKey.create))
        backstackDelegate.registerForLifecycleCallbacks(this)
        val backstackHolder: BackstackHolder = Injector.get().backstackHolder
        backstackHolder.setBackstack(backstackDelegate.backstack) // <-- make Backstack globally available through Dagger
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)
        Injector.get().inject(this)

        fragmentStateChanger = FragmentStateChanger(this, supportFragmentManager, R.id.container)
        backstackDelegate.setStateChanger(this)
    }

    fun setupViewsForKey(key: BaseKey<*>) {
        if (key.shouldShowUp()) {
        } else {
        }
        //val fragment: Fragment? = supportFragmentManager.findFragmentByTag(key.getFragmentTag())
    }

    override fun handleStateChange(
        stateChange: StateChange,
        completionCallback: StateChanger.Callback
    ) {
        if (!stateChange.isTopNewKeyEqualToPrevious) {
            fragmentStateChanger.handleStateChange(stateChange)

            setupViewsForKey(stateChange.topNewKey())
            //val title: String = stateChange.topNewKey<BaseKey<*>>().title(resources)!!
        }
        completionCallback.stateChangeComplete()
    }

    override fun onBackPressed() {
        if (!backstackDelegate.backstack.goBack())
            super.onBackPressed()
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
