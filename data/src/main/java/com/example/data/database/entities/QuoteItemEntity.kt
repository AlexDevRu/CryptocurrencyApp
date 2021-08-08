package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "quotes", foreignKeys = [ForeignKey(
    entity = CurrencyEntity::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("currencyId"),
    onDelete = ForeignKey.CASCADE
)])
data class QuoteItemEntity(
    @PrimaryKey var id: UUID = UUID.randomUUID(),
    var key: String,
    var marketCap: Double,
    var percentChange1h: Double,
    var percentChange24h: Double,
    var percentChange7d: Double,
    var percentChange30d: Double,
    var percentChange60d: Double,
    var percentChange90d: Double,
    var price: Double,
    var volume24h: Double,
    var currencyId: Int = 0
)