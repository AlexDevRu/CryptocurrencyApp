package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "currencies")
data class CurrencyEntity(
    @PrimaryKey var id: Int,
    var name: String? = null,
    var symbol: String? = null,
    var lastUpdated: Date,
    val cmcRank: Int = 0,
    val circulatingSupply: Double = 0.0,
    val dateAdded: Date = Date(),
    val maxSupply: Double = 0.0,
    val marketPairs: Int = 0,
    val tags: List<String>? = null,
    val totalSupply: Double = 0.0,
)