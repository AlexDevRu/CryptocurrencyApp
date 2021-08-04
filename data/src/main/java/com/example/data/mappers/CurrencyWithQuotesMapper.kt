package com.example.data.mappers

import com.example.data.database.entities.CurrencyWithQuotes
import com.example.domain.mappers.IMapper
import com.example.domain.models.Currency
import com.example.domain.models.QuoteItem

object CurrencyWithQuotesMapper: IMapper<CurrencyWithQuotes, Currency> {
    override fun toModel(entity: CurrencyWithQuotes): Currency {
        val quotes = mutableMapOf<String, QuoteItem>()
        for(quote in entity.quotes)
            quotes.put(quote.key, QuoteItem(
                last_updated = quote.last_updated,
                market_cap = quote.market_cap,
                percent_change_1h = quote.percent_change_1h,
                percent_change_24h = quote.percent_change_24h,
                percent_change_30d = quote.percent_change_30d,
                percent_change_60d = quote.percent_change_60d,
                percent_change_7d = quote.percent_change_7d,
                percent_change_90d = quote.percent_change_90d,
                price = quote.price,
                volume_24h = quote.volume_24h
            ))

        return Currency(
            entity.currency.id,
            entity.currency.name,
            entity.currency.symbol,
            quotes
        )
    }

    override fun fromModel(model: Currency): CurrencyWithQuotes {
        TODO("Not yet implemented")
    }
}