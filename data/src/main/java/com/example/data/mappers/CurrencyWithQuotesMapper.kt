package com.example.data.mappers

import com.example.data.database.entities.CurrencyWithQuotes
import com.example.domain.models.Currency
import com.example.domain.models.QuoteItem

fun CurrencyWithQuotes.toModel(): Currency {
    val quotesModel = mutableMapOf<String, QuoteItem>()
    for(quote in quotes)
        quotesModel.put(quote.key, QuoteItem(
            marketCap = quote.marketCap,
            percentChange1h = quote.percentChange1h,
            percentChange24h = quote.percentChange24h,
            percentChange7d = quote.percentChange7d,
            percentChange30d = quote.percentChange30d,
            percentChange60d = quote.percentChange60d,
            percentChange90d = quote.percentChange90d,
            price = quote.price,
            volume24h = quote.volume24h
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
        quotesModel
    )
}