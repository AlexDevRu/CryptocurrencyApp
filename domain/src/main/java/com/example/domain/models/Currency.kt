package com.example.domain.models

import java.util.*

data class Currency(
    val id: Int,
    val name: String? = null,
    val symbol: String? = null,
    val lastUpdated: Date = Date(),
    val cmcRank: Int = 0,
    val circulatingSupply: Double = 0.0,
    val dateAdded: Date = Date(),
    val maxSupply: Double = 0.0,
    val marketPairs: Int = 0,
    val tags: List<String>? = null,
    val totalSupply: Double = 0.0,
    val quote: Map<String, QuoteItem> = mapOf()
)