package com.intellisrc.mobiledeveloperchallenge.data

data class CurrencyModel(
    var success: Boolean = false,
    var terms: String = "",
    var privacy: String = "",
    var currencies: Map<String, String>
)