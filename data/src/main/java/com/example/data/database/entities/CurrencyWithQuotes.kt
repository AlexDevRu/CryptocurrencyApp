package com.example.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CurrencyWithQuotes (
    @Embedded
    val currency: CurrencyEntity,

    @Relation(parentColumn = "id", entityColumn = "currencyId")
    var quotes: List<QuoteItemEntity> = emptyList()
)