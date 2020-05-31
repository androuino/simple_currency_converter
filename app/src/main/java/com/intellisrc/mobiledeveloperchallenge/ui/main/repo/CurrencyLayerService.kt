package com.intellisrc.mobiledeveloperchallenge.ui.main.repo

import com.intellisrc.mobiledeveloperchallenge.Constants
import com.intellisrc.mobiledeveloperchallenge.data.ConversionDataModel
import com.intellisrc.mobiledeveloperchallenge.data.CurrencyModel
import com.intellisrc.mobiledeveloperchallenge.data.HistoricalDataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyLayerService {
    @GET(Constants.LIST)
    fun getCurrencies(): Call<CurrencyModel>

    @GET(Constants.HISTORICAL)
    fun getHistoricalData(@Query("date") date: String): Call<HistoricalDataModel>

    @GET(Constants.CONVERT)
    fun getConversionResult(@Query("from") from: String, @Query("to") to: String, @Query("amount") amount: Double): Call<ConversionDataModel>
}