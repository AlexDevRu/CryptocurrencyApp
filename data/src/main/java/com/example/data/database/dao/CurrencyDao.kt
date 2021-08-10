package com.example.data.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
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
        select * from currencies, quotes where 
        currencyId=currencies.id and
        name like :query and
        (price between :priceMin and :priceMax) and
        (marketCap between :marketCapMin and :marketCapMax)""")
    fun getCurrencies(
        query: String,
        priceMin: Double, priceMax: Double,
        marketCapMin: Double, marketCapMax: Double
    ): PagingSource<Int, CurrencyWithQuotes>


    @Query("select * from currencies where id=:id")
    suspend fun getCurrencyById(id: Int): CurrencyWithQuotes?


    @Query("select * from currencies order by lastUpdated desc limit 1")
    suspend fun getLatestCurrency(): CurrencyWithQuotes?


    suspend fun insertAll(currencies: List<CurrencyWithQuotes>) {
        for(currencyWithQuotes in currencies) {
            insert(currencyWithQuotes)
        }
    }

    suspend fun insert(currencyWithQuotes: CurrencyWithQuotes) {
        _insertCurrency(currencyWithQuotes.currency)
        for(quote in currencyWithQuotes.quotes)
            quote.currencyId = currencyWithQuotes.currency.id
        _insertQuotes(currencyWithQuotes.quotes)
    }

    @Query("delete from currencies")
    suspend fun clearAll()

    @Query("delete from currencies where id=:id")
    suspend fun deleteCurrencyById(id: Int)

    @Transaction
    suspend fun updateCurrency(currencyWithQuotes: CurrencyWithQuotes) {
        deleteCurrencyById(currencyWithQuotes.currency.id)
        insert(currencyWithQuotes)
    }


    @RawQuery(observedEntities = [CurrencyEntity::class, QuoteItemEntity::class])
    fun getAll(query: SupportSQLiteQuery): PagingSource<Int, CurrencyWithQuotes>
}