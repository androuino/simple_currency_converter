package com.intellisrc.mobiledeveloperchallenge.ui.main

import androidx.lifecycle.*
import com.intellisrc.mobiledeveloperchallenge.data.CurrencyModel
import com.intellisrc.mobiledeveloperchallenge.data.HistoricalDataModel
import com.intellisrc.mobiledeveloperchallenge.room.entity.RatesEntity
import com.intellisrc.mobiledeveloperchallenge.ui.base.BaseViewModel
import com.intellisrc.mobiledeveloperchallenge.ui.main.repo.CurrencyLayerService
import com.intellisrc.mobiledeveloperchallenge.utils.RxBus
import com.zhuinden.simplestack.Backstack
import kotlinx.coroutines.launch
import javax.inject.Inject

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
        /**
         * Any coroutine launched in this scope is automatically canceled if the ViewModel is cleared
         */
        viewModelScope.launch {
            getCurrencies()
            getHistoricalData()
        }
    }

    /**
     * Get currencies as List
     */
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

    /**
     * Get Historical data and get the quotes Map to get the latest currency rates
     */
    fun getHistoricalData() {
        if (service != null) {
            currencyLayerImplRepo.getHistoricalData(service)
            RxBus.subscribe((RxBus.HISTORICAL), this) { value ->
                value as HistoricalDataModel
                liveDataHistorical.postValue(value)
                if (roomDataSource.ratesDao().countAll() <= 0) {
                    value.quotes.forEach { (c, r) ->
                        val ratesEntity = RatesEntity()
                        ratesEntity.currency = c
                        ratesEntity.rate = r
                        ratesEntity.currencyType = ""
                        roomDataSource.ratesDao().insert(ratesEntity)
                    }
                } else {
                    value.quotes.forEach { (c, r) ->
                        val ratesEntity = RatesEntity()
                        ratesEntity.currency = c
                        ratesEntity.rate = r
                        ratesEntity.currencyType = ""
                        roomDataSource.ratesDao().update(ratesEntity)
                    }
                }
            }
        }
    }

    /**
     * TODO: Conversion doesn't work if not monthly subscribe
     */
    /*fun getConversionResult(from: String, to: String, amount: Double) {
        if (service != null) {
            currencyLayerImplRepo.currencyConversion(service, from, to, amount)
            RxBus.subscribe((RxBus.CONVERSION), this) { value ->

            }
        }
    }*/

    fun getRatesEntity(): LiveData<List<RatesEntity>> {
        return currencyLayerImplRepo.getRatesEntity()
    }

    override fun onChanged(t: String?) {

    }

    companion object {
        const val TAG = "MainFragmentViewModel"
    }
}