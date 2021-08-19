package com.example.data.mappers

import com.example.data.api.responses.CurrencyResponse
import com.example.data.database.entities.CurrencyWithQuotes
import com.example.domain.models.Currency
import java.util.*

fun Currency.toCurrencyWithQuotes(): CurrencyWithQuotes {
    val quotes = quote.map { it.value.toEntity(it.key) }
    return CurrencyWithQuotes(toEntity(), quotes)
}

fun CurrencyResponse.toCurrencyWithQuotes(addedToFavorite: Date?): CurrencyWithQuotes {
    val quotes = quote.map { it.value.toEntity(it.key) }
    val entity = toEntity()
    entity.addedToFavorite = addedToFavorite
    return CurrencyWithQuotes(entity, quotes)
}