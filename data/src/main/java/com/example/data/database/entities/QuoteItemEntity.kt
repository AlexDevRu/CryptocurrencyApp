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
    var last_updated: String,
    var market_cap: Double,
    var percent_change_1h: Double,
    var percent_change_24h: Double,
    var percent_change_30d: Double,
    var percent_change_60d: Double,
    var percent_change_7d: Double,
    var percent_change_90d: Double,
    var price: Double,
    var volume_24h: Double,
    var currencyId: Int = 0
)