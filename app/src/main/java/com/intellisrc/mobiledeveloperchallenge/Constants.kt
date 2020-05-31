package com.intellisrc.mobiledeveloperchallenge

import java.text.SimpleDateFormat
import java.time.LocalDate

object Constants {
    const val MAIN_FRAGMENT = 0

    const val CURRENCY_LAYER_API_KEY = "9ee010d5c65e6d8d0601165c3f74d063"
    const val BASE_URL = "http://api.currencylayer.com/"
    const val LIST = "${BASE_URL}list?access_key=$CURRENCY_LAYER_API_KEY"
    const val LIVE = "${BASE_URL}live?"
    const val HISTORICAL = "${BASE_URL}historical?access_key=$CURRENCY_LAYER_API_KEY"
    const val CONVERT = "${BASE_URL}convert?access_key=$CURRENCY_LAYER_API_KEY"
    const val TIME_FRAME = "${BASE_URL}timeframe?"
    const val CHANGE = "${BASE_URL}change?"
}