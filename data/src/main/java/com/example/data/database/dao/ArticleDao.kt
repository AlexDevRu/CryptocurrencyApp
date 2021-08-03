package com.example.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.database.entities.ArticleEntity

@Dao
interface ArticleDao {
    @Query("SELECT * from articles")
    fun getArticles(): PagingSource<Int, ArticleEntity>

    @Insert
    suspend fun insertAll(articles: List<ArticleEntity>)

    @Query("DELETE FROM articles")
    suspend fun clearAll()
}