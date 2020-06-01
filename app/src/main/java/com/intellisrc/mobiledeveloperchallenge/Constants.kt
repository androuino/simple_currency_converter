package com.intellisrc.mobiledeveloperchallenge

object Constants {
    const val MAIN_FRAGMENT = 0

    const val DATABASE_NAME = "rates.db"

    const val CURRENCY_LAYER_API_KEY = "f715ff4a45f884f9dfb1dbccc8bee0ea"
    const val BASE_URL = "http://api.currencylayer.com/"
    const val LIST = "${BASE_URL}list?access_key=$CURRENCY_LAYER_API_KEY"
    const val LIVE = "${BASE_URL}live?"
    const val HISTORICAL = "${BASE_URL}historical?access_key=$CURRENCY_LAYER_API_KEY"
    const val CONVERT = "${BASE_URL}convert?access_key=$CURRENCY_LAYER_API_KEY"
    const val TIME_FRAME = "${BASE_URL}timeframe?access_key=$CURRENCY_LAYER_API_KEY"
    const val CHANGE = "${BASE_URL}change?access_key=$CURRENCY_LAYER_API_KEY"
}