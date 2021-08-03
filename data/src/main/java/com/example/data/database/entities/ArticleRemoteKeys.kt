package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "article_remote_keys")
data class ArticleRemoteKeys(
    @PrimaryKey var articleId: UUID,
    var prevKey: Int?,
    var nextKey: Int?
)