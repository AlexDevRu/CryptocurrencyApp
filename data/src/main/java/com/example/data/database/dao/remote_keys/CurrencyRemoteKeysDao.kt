package com.example.data.database.dao.remote_keys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.CurrencyRemoteKeys

@Dao
interface CurrencyRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<CurrencyRemoteKeys>)

    @Query("SELECT * FROM currency_remote_keys WHERE currencyId = :currencyId")
    suspend fun remoteKeysCurrencyId(currencyId: Int): CurrencyRemoteKeys?

    @Query("DELETE FROM currency_remote_keys")
    suspend fun clearRemoteKeys()
}