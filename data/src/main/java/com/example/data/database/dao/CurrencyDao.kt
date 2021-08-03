package com.example.data.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.data.database.entities.CurrencyEntity
import com.example.data.database.entities.CurrencyWithQuotes
import com.example.data.database.entities.QuoteItemEntity

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun _insertCurrency(currency: CurrencyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun _insertQuotes(quotes: List<QuoteItemEntity>)

    @Query("SELECT * from currencies")
    fun getCurrencies(): PagingSource<Int, CurrencyWithQuotes>

    @Transaction
    suspend fun insertAll(currency: CurrencyEntity, quotes: List<QuoteItemEntity>) {
        _insertCurrency(currency)
        for(quote in quotes)
            quote.currencyId = currency.id
        _insertQuotes(quotes)
    }

    @Query("DELETE FROM currencies")
    suspend fun clearAll()
}