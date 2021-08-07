package com.example.data.mappers

import com.example.data.api.responses.CurrencyResponse
import com.example.data.database.entities.CurrencyEntity
import com.example.domain.models.Currency
import java.text.SimpleDateFormat
import java.util.*

fun CurrencyResponse.toModel(): Currency {
    return Currency(
        id,
        name,
        symbol,
        SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss",
            Locale.getDefault()
        ).parse(last_updated)!!,
        cmc_rank,
        circulating_supply,
        SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss",
            Locale.getDefault()
        ).parse(date_added)!!,
        max_supply,
        num_market_pairs,
        tags,
        total_supply,
        quote
    )
}

fun CurrencyResponse.toEntity(): CurrencyEntity {
    return CurrencyEntity(
        id,
        name,
        symbol,
        SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss",
            Locale.getDefault()
        ).parse(last_updated)!!,
        cmc_rank,
        circulating_supply,
        SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss",
            Locale.getDefault()
        ).parse(date_added)!!,
        max_supply,
        num_market_pairs,
        tags,
        total_supply
    )
}