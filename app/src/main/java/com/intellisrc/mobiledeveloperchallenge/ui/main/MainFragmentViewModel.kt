package com.intellisrc.mobiledeveloperchallenge.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.intellisrc.mobiledeveloperchallenge.data.CurrencyModel
import com.intellisrc.mobiledeveloperchallenge.data.HistoricalDataModel
import com.intellisrc.mobiledeveloperchallenge.ui.base.BaseViewModel
import com.intellisrc.mobiledeveloperchallenge.ui.main.repo.CurrencyLayerService
import com.intellisrc.mobiledeveloperchallenge.utils.RxBus
import com.zhuinden.simplestack.Backstack
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class MainFragmentViewModel @Inject constructor(private val backstack: Backstack) : BaseViewModel(), Observer<String> {
    private val liveDataCurrencies = MutableLiveData<ArrayList<String>>()
    private val liveDataHistorical = MutableLiveData<HistoricalDataModel>()

    private var service = retrofit?.create(CurrencyLayerService::class.java)

    val getCurrency = liveDataCurrencies.switchMap {
        liveData { emit(it) }
    }
    val getHistoricalData = liveDataHistorical.switchMap {
        liveData { emit(it) }
    }

    init {
        viewModelScope.launch {
            getCurrencies()
            getHistoricalData()
        }
    }

    private fun getCurrencies() {
        if (service != null) {
            currencyLayerImplRepo.getCurrencies(service)
            val currencyList = ArrayList<String>()
            RxBus.subscribe((RxBus.OBJECT), this) { value ->
                value as CurrencyModel
                value.currencies.forEach { (k, _) ->
                    currencyList.add(k)
                }
                liveDataCurrencies.postValue(currencyList)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getHistoricalData() {
        if (service != null) {
            currencyLayerImplRepo.getHistoricalData(service)
            RxBus.subscribe((RxBus.HISTORICAL), this) { value ->
                value as HistoricalDataModel
                liveDataHistorical.postValue(value)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getConversionResult(from: String, to: String, amount: Double) {
        if (service != null) {
            currencyLayerImplRepo.currencyConversion(service, from, to, amount)
            RxBus.subscribe((RxBus.CONVERSION), this) { value ->

            }
        }
    }

    override fun onChanged(t: String?) {
        TODO("Not yet implemented")
    }

    companion object {
        const val TAG = "MainFragmentViewModel"
    }
}