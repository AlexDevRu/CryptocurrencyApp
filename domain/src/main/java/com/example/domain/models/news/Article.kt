package com.example.domain.models.news

import java.util.*

data class Article(
    val sourceName: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val published: Date,
    val id: UUID = UUID.randomUUID()
)