package com.example.domain.models

data class CurrencyParameters(
    var type: String = "",
    var tag: String = "",
    var priceMin: Double = 0.0,
    var priceMax: Double = 0.0,
    var marketCapMin: Double = 0.0,
    var marketCapMax: Double = 0.0,
    var sortType: String = "",
    var sortDir: String = "",
    var searchQuery: String = ""
)
