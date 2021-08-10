package com.example.domain.models

data class CurrencyParameters(
    var type: String = "all",
    var tag: String = "all",
    var priceMin: Double = 0.0,
    var priceMax: Double = 1000000000.0,
    var marketCapMin: Double = 0.0,
    var marketCapMax: Double = 1000000000.0,
    var sortType: String = "market_cap",
    var sortDir: String = "desc",
    var searchQuery: String = ""
)
