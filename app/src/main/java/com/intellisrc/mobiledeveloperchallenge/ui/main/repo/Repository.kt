package com.intellisrc.mobiledeveloperchallenge.ui.main.repo

import androidx.lifecycle.LiveData
import com.intellisrc.mobiledeveloperchallenge.room.entity.RatesEntity

interface Repository {
    fun getLatestRates(service: CurrencyRatesService?)
    fun getCurrencies(service: CurrencyLayerService?)
    fun getHistoricalData(service: CurrencyLayerService?)
    fun currencyConversion(service: CurrencyLayerService?, from: String, to: String, amount: Double)
    fun getRatesEntity(): LiveData<List<RatesEntity>>
}