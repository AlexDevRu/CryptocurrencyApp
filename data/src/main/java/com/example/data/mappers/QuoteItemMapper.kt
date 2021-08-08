package com.example.data.mappers

import com.example.data.api.responses.QuoteItemResponse
import com.example.data.database.entities.QuoteItemEntity
import com.example.domain.models.QuoteItem

fun QuoteItemResponse.toEntity(key: String): QuoteItemEntity {
    return QuoteItemEntity(
        key = key,
        marketCap = market_cap,
        percentChange1h = percent_change_1h,
        percentChange24h = percent_change_24h,
        percentChange7d = percent_change_7d,
        percentChange30d = percent_change_30d,
        percentChange60d = percent_change_60d,
        percentChange90d = percent_change_90d,
        price = price,
        volume24h = volume_24h
    )
}

fun QuoteItemResponse.toModel(): QuoteItem {
    return QuoteItem(
        marketCap = market_cap,
        percentChange1h = percent_change_1h,
        percentChange24h = percent_change_24h,
        percentChange7d = percent_change_7d,
        percentChange30d = percent_change_30d,
        percentChange60d = percent_change_60d,
        percentChange90d = percent_change_90d,
        price = price,
        volume24h = volume_24h
    )
}
