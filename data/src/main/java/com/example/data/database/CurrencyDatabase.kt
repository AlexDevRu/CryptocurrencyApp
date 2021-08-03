package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.database.converters.ArticleTypeConverters
import com.example.data.database.dao.ArticleDao
import com.example.data.database.dao.CurrencyDao
import com.example.data.database.dao.remote_keys.ArticleRemoteKeysDao
import com.example.data.database.dao.remote_keys.CurrencyRemoteKeysDao
import com.example.data.database.entities.*

@Database(
    entities = [
        CurrencyEntity::class,
        QuoteItemEntity::class,
        CurrencyRemoteKeys::class,
        ArticleRemoteKeys::class,
        ArticleEntity::class
    ],
    version = 1
)
@TypeConverters(ArticleTypeConverters::class)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun currencyRemoteKeysDao(): CurrencyRemoteKeysDao
    abstract fun articleDao(): ArticleDao
    abstract fun articleRemoteKeysDao(): ArticleRemoteKeysDao
}