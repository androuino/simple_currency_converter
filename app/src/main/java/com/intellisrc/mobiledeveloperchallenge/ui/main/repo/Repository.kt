package com.intellisrc.mobiledeveloperchallenge.ui.main.repo

import com.intellisrc.mobiledeveloperchallenge.data.CurrencyModel

interface Repository {
    fun getCurrencies(service: CurrencyLayerService?)
    fun getHistoricalData(service: CurrencyLayerService?)
    fun currencyConversion(service: CurrencyLayerService?, from: String, to: String, amount: Double)
}