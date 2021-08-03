package com.example.data.mappers

import com.example.data.database.entities.QuoteItemEntity
import com.example.domain.mappers.IMapper
import com.example.domain.models.QuoteItem

object QuoteItemMapper: IMapper<List<QuoteItemEntity>, Map<String, QuoteItem>> {
    override fun toModel(entity: List<QuoteItemEntity>): Map<String, QuoteItem> {
            return mapOf()
    /*return QuoteItem(
            entity.last_updated,
            entity.market_cap,
            entity.percent_change_1h,
            entity.percent_change_24h,
            entity.percent_change_30d,
            entity.percent_change_60d,
            entity.percent_change_7d,
            entity.percent_change_90d,
            entity.price,
            entity.volume_24h
        )*/
    }

    override fun fromModel(model: Map<String, QuoteItem>): List<QuoteItemEntity> {

        val quotes = mutableListOf<QuoteItemEntity>()

        for(quote in model.entries) {
            val entity = QuoteItemEntity(
                id = quote.key,
                last_updated = quote.value.last_updated,
                market_cap = quote.value.market_cap,
                percent_change_1h = quote.value.percent_change_1h,
                percent_change_24h = quote.value.percent_change_24h,
                percent_change_7d = quote.value.percent_change_7d,
                percent_change_30d = quote.value.percent_change_30d,
                percent_change_60d = quote.value.percent_change_60d,
                percent_change_90d = quote.value.percent_change_90d,
                price = quote.value.price,
                volume_24h = quote.value.volume_24h
            )
            quotes.add(entity)
        }

        return quotes
    }
}