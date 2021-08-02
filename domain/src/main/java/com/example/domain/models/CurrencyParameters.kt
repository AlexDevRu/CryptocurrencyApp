package com.example.domain.models

data class CurrencyParameters(
    var type: String = "",
    var tag: String = "",
    var priceMin: Float = 0f,
    var priceMax: Float = 0f,
    var marketCapMin: Float = 0f,
    var marketCapMax: Float = 0f,
    var sortType: String = "",
    var sortDir: String = "",
    var searchQuery: String = ""
)