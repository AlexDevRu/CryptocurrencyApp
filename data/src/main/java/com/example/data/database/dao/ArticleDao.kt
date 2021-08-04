package com.example.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.ArticleEntity

@Dao
interface ArticleDao {
    @Query("SELECT * from articles where title like :query or description like :query")
    fun getArticlesByQuery(query: String): PagingSource<Int, ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<ArticleEntity>)

    @Query("DELETE FROM articles")
    suspend fun clearAll()
}