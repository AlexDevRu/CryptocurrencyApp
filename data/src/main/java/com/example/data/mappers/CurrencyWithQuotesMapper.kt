package com.example.data.mappers

import com.example.data.database.entities.CurrencyWithQuotes
import com.example.domain.models.Currency
import com.example.domain.models.QuoteItem

fun CurrencyWithQuotes.toModel(): Currency {
    val quotes = mutableMapOf<String, QuoteItem>()
    for(quote in quotes)
        quotes.put(quote.key, QuoteItem(
            lastUpdated = quote.value.lastUpdated,
            marketCap = quote.value.marketCap,
            percentChange1h = quote.value.percentChange1h,
            percentChange24h = quote.value.percentChange24h,
            percentChange7d = quote.value.percentChange7d,
            percentChange30d = quote.value.percentChange30d,
            percentChange60d = quote.value.percentChange60d,
            percentChange90d = quote.value.percentChange90d,
            price = quote.value.price,
            volume24h = quote.value.volume24h
        ))

    return Currency(
        currency.id,
        currency.name,
        currency.symbol,
        currency.lastUpdated,
        currency.cmcRank,
        currency.circulatingSupply,
        currency.dateAdded,
        currency.maxSupply,
        currency.marketPairs,
        currency.tags,
        currency.totalSupply,
        quotes
    )
}