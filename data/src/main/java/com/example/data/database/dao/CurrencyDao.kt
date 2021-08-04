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

    @Query("""
        SELECT * from currencies, quotes where 
        name like :query and 
        currencyId=currencies.id and 
        (price between :priceMin and :priceMax) and
        (market_cap between :marketCapMin and :marketCapMax)""")
    fun getCurrencies(
        query: String,
        priceMin: Float, priceMax: Float,
        marketCapMin: Float, marketCapMax: Float
    ): PagingSource<Int, CurrencyWithQuotes>


    @Query("SELECT * from currencies order by last_updated desc limit 1")
    suspend fun getLatestCurrency(): CurrencyWithQuotes?


    suspend fun insertAll(currencies: List<CurrencyWithQuotes>) {
        for(curr in currencies) {
            _insertCurrency(curr.currency)
            for(quote in curr.quotes)
                quote.currencyId = curr.currency.id
            _insertQuotes(curr.quotes)
        }
    }

    @Query("DELETE FROM currencies")
    suspend fun clearAll()
}