package com.example.data.mappers

import com.example.data.api.responses.news.ArticleResponse
import com.example.data.database.entities.ArticleEntity
import com.example.domain.models.news.Article
import java.text.SimpleDateFormat
import java.util.*

fun ArticleResponse.toModel(): Article {
    return Article(
        source.name,
        title,
        description,
        url,
        image,
        SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss",
            Locale.getDefault()
        ).parse(publishedAt)!!
    )
}

fun ArticleResponse.toEntity(): ArticleEntity {
    return ArticleEntity(
        sourceName = source.name,
        title = title,
        description = description,
        url = url,
        urlToImage = image,
        published = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss",
            Locale.getDefault()
        ).parse(publishedAt)!!
    )
}