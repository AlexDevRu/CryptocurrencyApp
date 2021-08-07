package com.example.data.mappers

import com.example.data.database.entities.QuoteItemEntity
import com.example.domain.models.QuoteItem

fun QuoteItem.toEntity(key: String): QuoteItemEntity {
    return QuoteItemEntity(
        key = key,
        lastUpdated = lastUpdated,
        marketCap = marketCap,
        percentChange1h = percentChange1h,
        percentChange24h = percentChange24h,
        percentChange7d = percentChange7d,
        percentChange30d = percentChange30d,
        percentChange60d = percentChange60d,
        percentChange90d = percentChange90d,
        price = price,
        volume24h = volume24h
    )
}
