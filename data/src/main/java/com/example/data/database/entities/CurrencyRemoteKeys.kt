package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_remote_keys")
data class CurrencyRemoteKeys(
    @PrimaryKey var currencyId: Int,
    var prevKey: Int?,
    var nextKey: Int?
)