package com.example.domain.models

import java.util.*

data class Currency(
    val id: Int,
    val name: String? = null,
    val symbol: String? = null,
    val last_updated: Date = Date(),
    val quote: Map<String, QuoteItem>
)