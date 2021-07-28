package com.example.domain.models

data class Currency(
    val id: Int,
    val name: String? = null,
    val symbol: String? = null,
    val quote: Map<String, QuoteItem>
)