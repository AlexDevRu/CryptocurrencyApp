package com.example.data.api.responses

import com.example.domain.models.QuoteItem

data class CurrencyResponse(
    val circulating_supply: Double,
    val cmc_rank: Int,
    val date_added: String,
    val id: Int,
    val last_updated: String,
    val max_supply: Double,
    val name: String,
    val num_market_pairs: Int,
    val quote: Map<String, QuoteItem>,
    val slug: String,
    val symbol: String,
    val tags: List<String>,
    val total_supply: Double
)