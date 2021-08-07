package com.example.data.mappers

import com.example.data.database.entities.ArticleEntity
import com.example.domain.models.news.Article

fun ArticleEntity.toModel(): Article {
    return Article(
        sourceName,
        title,
        description,
        url,
        urlToImage,
        published
    )
}

fun Article.toEntity(): ArticleEntity {
    return ArticleEntity(
        sourceName = sourceName,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        published = published
    )
}