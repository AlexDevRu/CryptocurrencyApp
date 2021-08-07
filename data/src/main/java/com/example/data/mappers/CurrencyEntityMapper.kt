package com.example.data.mappers

import com.example.data.database.entities.CurrencyEntity
import com.example.domain.models.Currency

fun CurrencyEntity.toModel(): Currency {
    return Currency(
        id,
        name,
        symbol,
        lastUpdated,
        cmcRank,
        circulatingSupply,
        dateAdded,
        maxSupply,
        marketPairs,
        tags,
        totalSupply
    )
}

fun Currency.toEntity(): CurrencyEntity {
    return CurrencyEntity(
        id,
        name,
        symbol,
        lastUpdated,
        cmcRank,
        circulatingSupply,
        dateAdded,
        maxSupply,
        marketPairs,
        tags,
        totalSupply
    )
}