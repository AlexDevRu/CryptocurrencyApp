package com.example.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation

class CurrencyWithQuotes {
    @Embedded
    lateinit var currency: CurrencyEntity

    @Relation(parentColumn = "id", entityColumn = "currencyId")
    lateinit var quotes: List<QuoteItemEntity>
}