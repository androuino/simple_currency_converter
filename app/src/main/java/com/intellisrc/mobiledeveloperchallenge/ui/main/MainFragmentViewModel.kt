package com.intellisrc.mobiledeveloperchallenge.ui.main

import androidx.lifecycle.*
import com.intellisrc.mobiledeveloperchallenge.data.CurrencyModel
import com.intellisrc.mobiledeveloperchallenge.data.ExchangeRatesDataModel
import com.intellisrc.mobiledeveloperchallenge.data.HistoricalDataModel
import com.intellisrc.mobiledeveloperchallenge.data.RatesDataModel
import com.intellisrc.mobiledeveloperchallenge.room.entity.RatesEntity
import com.intellisrc.mobiledeveloperchallenge.ui.base.BaseViewModel
import com.intellisrc.mobiledeveloperchallenge.ui.main.repo.CurrencyRatesService
import com.intellisrc.mobiledeveloperchallenge.utils.RxBus
import com.zhuinden.simplestack.Backstack
import kotlinx.coroutines.launch
import okhttp3.internal.connection.Exchange
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(private val backstack: Backstack) : BaseViewModel(), Observer<String> {
    private val liveDataLatestRates = MutableLiveData<RatesDataModel>()
    private val liveDataHistorical = MutableLiveData<HistoricalDataModel>()
    private val liveDataExchangeRates = MutableLiveData<ExchangeRatesDataModel>()

    private var service = retrofit?.create(CurrencyRatesService::class.java)

    val getLatestRatesData = liveDataLatestRates.switchMap {
        liveData { emit(it) }
    }
    val getHistoricalData = liveDataHistorical.switchMap {
        liveData { emit(it) }
    }
    val getExchangeRatesData = liveDataExchangeRates.switchMap {
        liveData { emit(it) }
    }

    init {
        /**
         * Any coroutine launched in this scope is automatically canceled if the ViewModel is cleared
         */
        viewModelScope.launch {
            getLatestRates()
            getHistoricalData()
        }
    }

    /**
     * Get currencies as List
     */
    private fun getLatestRates() {
        if (service != null) {
            currencyLayerImplRepo.getLatestRates(service)
            RxBus.subscribe(RxBus.LATEST_RATES, this) {
                it as RatesDataModel
                liveDataLatestRates.postValue(it)
            }
        }
    }

    fun getExchangeRates(currency: String) {
        if (service != null) {
            currencyLayerImplRepo.getExchangeRates(service, currency)
            RxBus.subscribe(RxBus.EXCHANGE_RATES, this) {
                it as ExchangeRatesDataModel
                liveDataExchangeRates.postValue(it)
            }
        }
    }

    /**
     * Get Historical data and get the quotes Map to get the latest currency rates
     */
    fun getHistoricalData() {
        if (service != null) {
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