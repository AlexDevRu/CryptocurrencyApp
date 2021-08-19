package com.example.data.mappers

import com.example.data.database.entities.CurrencyWithQuotes
import com.example.domain.models.Currency
import com.example.domain.models.QuoteItem

fun CurrencyWithQuotes.toModel(): Currency {
    val quotesModel = mutableMapOf<String, QuoteItem>()
    for(quote in quotes)
        quotesModel[quote.key] = quote.toModel()

    val model = currency.toModel()
    model.quote = quotesModel
    model.addedToFavorite = currency.addedToFavorite

    return model
}