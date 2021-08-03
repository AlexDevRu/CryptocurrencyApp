package com.example.data.database.dao.remote_keys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.ArticleRemoteKeys
import java.util.*

@Dao
interface ArticleRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<ArticleRemoteKeys>)

    @Query("SELECT * FROM article_remote_keys WHERE articleId = :articleId")
    suspend fun remoteKeysArticleId(articleId: UUID): ArticleRemoteKeys?

    @Query("DELETE FROM article_remote_keys")
    suspend fun clearRemoteKeys()
}