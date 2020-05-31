package com.intellisrc.mobiledeveloperchallenge.data

data class ConversionDataModel(
    var success: String = "",
    var terms: String = "",
    var privacy: String = "",
    var query: Map<String, Any>,
    var info: Map<String, Int>,
    var result: Double = 0.0
)