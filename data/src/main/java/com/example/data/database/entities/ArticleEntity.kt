package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val sourceName: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val published: Date,
)