package com.intellisrc.mobiledeveloperchallenge.ui.main.repo

import android.os.Build
import androidx.annotation.RequiresApi
import com.intellisrc.mobiledeveloperchallenge.data.ConversionDataModel
import com.intellisrc.mobiledeveloperchallenge.data.CurrencyModel
import com.intellisrc.mobiledeveloperchallenge.data.HistoricalDataModel
import com.intellisrc.mobiledeveloperchallenge.di.Injector
import com.intellisrc.mobiledeveloperchallenge.utils.RxBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyLayerImplRepo @Inject constructor() : Repository {

    init {
        Injector.get().inject(this)
    }

    // Get the list of currency here
    override fun getCurrencies(service: CurrencyLayerService?) {
        Timber.tag(TAG).d("getCurrencies")
        val call: Call<CurrencyModel> = service?.getCurrencies()!!
        call.enqueue(object : Callback<CurrencyModel> {
            override fun onResponse(call: Call<CurrencyModel>, response: Response<CurrencyModel>) {
                if (response.isSuccessful) {
                    RxBus.publish(RxBus.OBJECT, response.body()!!) // FIXME: RxBus is not a good solution
                }
            }

            override fun onFailure(call: Call<CurrencyModel>, t: Throwable) {
                Timber.tag(TAG).e("Error on getCurrencies->${t.message}")
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getHistoricalData(service: CurrencyLayerService?) {
        Timber.tag(TAG).d("getHistoricalData")
        val today = LocalDateTime.now().toLocalDate()
        val call: Call<HistoricalDataModel> = service?.getHistoricalData(today.toString())!!
        call.enqueue(object : Callback<HistoricalDataModel> {
            override fun onResponse(
                call: Call<HistoricalDataModel>,
                response: Response<HistoricalDataModel>
            ) {
                if (response.isSuccessful) {
                    RxBus.publish(RxBus.HISTORICAL, response.body()!!)
                }
            }

            override fun onFailure(call: Call<HistoricalDataModel>, t: Throwable) {
                Timber.tag(TAG).e("Error in getHistoricalData->${t.message}")
            }
        })
    }

    // FIXME: This doesn't work unless paid access to the API
    @RequiresApi(Build.VERSION_CODES.O)
    override fun currencyConversion(service: CurrencyLayerService?, from: String, to: String, amount: Double) {
        Timber.tag(TAG).d("currencyConversion")
        val today = LocalDateTime.now().toLocalDate() // TODO: keep this for Historical conversion
        val call: Call<ConversionDataModel> = service?.getConversionResult(from, to, amount)!!
        call.enqueue(object : Callback<ConversionDataModel> {
            override fun onResponse(
                call: Call<ConversionDataModel>,
                response: Response<ConversionDataModel>
            ) {
                if (response.isSuccessful) {
                    Timber.tag(TAG).i("${response.body()?.result}")
                    RxBus.publish(RxBus.CONVERSION, response.body()!!)
                }
            }

            override fun onFailure(call: Call<ConversionDataModel>, t: Throwable) {
                Timber.tag(TAG).e("Error in currencyConversion->${t.message}")
            }
        })
    }

    companion object {
        const val TAG = "CurrencyLayerImplRepo"
    }
}