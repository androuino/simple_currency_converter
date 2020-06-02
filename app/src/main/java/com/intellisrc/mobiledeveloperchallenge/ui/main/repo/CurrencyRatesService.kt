package com.intellisrc.mobiledeveloperchallenge.ui.main.repo

import com.intellisrc.mobiledeveloperchallenge.Constants
import com.intellisrc.mobiledeveloperchallenge.data.ExchangeRatesDataModel
import com.intellisrc.mobiledeveloperchallenge.data.RatesDataModel
import retrofit2.Call
import retrofit2.http.*

interface CurrencyRatesService {
    @GET(Constants.LATEST)
    fun getRates(): Call<RatesDataModel>

    @GET
    fun getRates(@Url url: String): Call<ExchangeRatesDataModel>
}