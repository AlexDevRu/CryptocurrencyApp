package com.example.domain.models

data class CurrencyParameters(
    var type: String = "all",
    var tag: String = "all",
    var priceMin: Float = 0f,
    var priceMax: Float = 100000000000000000f,
    var marketCapMin: Float = 0f,
    var marketCapMax: Float = 100000000000000000f,
    var sortType: String = "market_cap",
    var sortDir: String = "desc",
    var searchQuery: String = ""
)