package com.intellisrc.mobiledeveloperchallenge.ui.main.repo

import androidx.lifecycle.LiveData
import com.intellisrc.mobiledeveloperchallenge.Constants
import com.intellisrc.mobiledeveloperchallenge.data.*
import com.intellisrc.mobiledeveloperchallenge.di.Injector
import com.intellisrc.mobiledeveloperchallenge.room.RoomDataSource
import com.intellisrc.mobiledeveloperchallenge.room.entity.RatesEntity
import com.intellisrc.mobiledeveloperchallenge.utils.RxBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyLayerImplRepo @Inject constructor() : Repository {
    @Inject
    lateinit var roomDataSource: RoomDataSource

    init {
        Injector.get().inject(this)
    }

    override fun getLatestRates(service: CurrencyRatesService?) {
        Timber.tag(TAG).d("getRates")
        val call: Call<RatesDataModel> = service?.getRates()!!
        call.enqueue(object : Callback<RatesDataModel> {
            override fun onResponse(
                call: Call<RatesDataModel>,
                response: Response<RatesDataModel>
            ) {
                if (response.isSuccessful)
                    RxBus.publish(RxBus.LATEST_RATES, response.body()!!)
            }

            override fun onFailure(call: Call<RatesDataModel>, t: Throwable) {
                Timber.tag(TAG).e("Error on getLatestRates->${t.message}")
            }
        })
    }

    override fun getExchangeRates(service: CurrencyRatesService?, currency: String) {
        Timber.tag(TAG).d("getExchangeRates")
        val url = "${Constants.EXCHANGE_RATES}USD,$currency"
        val call: Call<ExchangeRatesDataModel> = service?.getRates(url)!!
        call.enqueue(object : Callback<ExchangeRatesDataModel> {
            override fun onResponse(
                call: Call<ExchangeRatesDataModel>,
                response: Response<ExchangeRatesDataModel>
            ) {
                if (response.isSuccessful)
                    RxBus.publish(RxBus.EXCHANGE_RATES, response.body()!!)
            }

            override fun onFailure(call: Call<ExchangeRatesDataModel>, t: Throwable) {
                Timber.tag(TAG).e("Error on getExchangeRates->${t.message}")
            }
        })
    }

    // Get the list of currency here
    override fun getCurrencies(service: CurrencyLayerService?) {
        Timber.tag(TAG).d("getCurrencies")
        val call: Call<CurrencyModel> = service?.getCurrencies()!!
        call.enqueue(object : Callback<CurrencyModel> {
            override fun onResponse(call: Call<CurrencyModel>, response: Response<CurrencyModel>) {
                if (response.isSuccessful) {
                    if (response.body()?.success!!)
                        RxBus.publish(RxBus.OBJECT, response.body()!!) // FIXME: RxBus is not a good solution
                }
            }

            override fun onFailure(call: Call<CurrencyModel>, t: Throwable) {
                Timber.tag(TAG).e("Error on getCurrencies->${t.message}")
            }
        })
    }

    override fun getHistoricalData(service: CurrencyLayerService?) {
        Timber.tag(TAG).d("getHistoricalData")
        val today: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val call: Call<HistoricalDataModel> = service?.getHistoricalData(today)!!
        call.enqueue(object : Callback<HistoricalDataModel> {
            override fun onResponse(
                call: Call<HistoricalDataModel>,
                response: Response<HistoricalDataModel>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.success!!)
                        RxBus.publish(RxBus.HISTORICAL, response.body()!!)
                }
            }

            override fun onFailure(call: Call<HistoricalDataModel>, t: Throwable) {
                Timber.tag(TAG).e("Error in getHistoricalData->${t.message}")
            }
        })
    }

    // FIXME: This doesn't work unless paid access to the API
    override fun currencyConversion(service: CurrencyLayerService?, from: String, to: String, amount: Double) {
        Timber.tag(TAG).d("currencyConversion")
        val today: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val call: Call<ConversionDataModel> = service?.getConversionResult(from, to, amount)!!
        call.enqueue(object : Callback<ConversionDataModel> {
            override fun onResponse(
                call: Call<ConversionDataModel>,
                response: Response<ConversionDataModel>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.success!!)
                        RxBus.publish(RxBus.CONVERSION, response.body()!!)
                }
            }

            override fun onFailure(call: Call<ConversionDataModel>, t: Throwable) {
                Timber.tag(TAG).e("Error in currencyConversion->${t.message}")
            }
        })
    }

    override fun getRatesEntity(): LiveData<List<RatesEntity>> {
        return roomDataSource.ratesDao().getRates()
    }

    companion object {
        const val TAG = "CurrencyLayerImplRepo"
    }
}