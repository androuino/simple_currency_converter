package com.intellisrc.mobiledeveloperchallenge.data

data class LatestRatesDataModel(
    var currency: String = "",
    var rates: Double = 0.0,
    var currencyType: String = ""
)