package com.intellisrc.mobiledeveloperchallenge.data

data class ExchangeRatesDataModel(
    var rates: Map<String, Double>,
    var base: String = "",
    var date: String = ""
)