package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "currencies")
data class CurrencyEntity(
    @PrimaryKey var id: Int,
    var name: String? = null,
    var symbol: String? = null,
    var last_updated: Date
)