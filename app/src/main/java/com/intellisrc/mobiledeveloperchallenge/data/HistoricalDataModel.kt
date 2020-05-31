package com.intellisrc.mobiledeveloperchallenge.data

data class HistoricalDataModel(
    var success: Boolean = false,
    var terms: String = "",
    var privacy: String = "",
    var historical: Boolean = false,
    var date: String = "",
    var timestamp: Int = 0,
    var source: String = "",
    var quotes: Map<String, Double>
)