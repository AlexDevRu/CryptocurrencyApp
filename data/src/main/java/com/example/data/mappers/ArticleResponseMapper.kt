package com.example.data.mappers

import com.example.data.api.responses.news.ListingNewsResponse
import com.example.data.database.entities.ArticleEntity
import com.example.domain.mappers.IMapper
import com.example.domain.models.news.Article
import java.text.SimpleDateFormat
import java.util.*

object ArticleResponseMapper: IMapper<ListingNewsResponse.ArticleResponse, Article> {
    override fun toModel(entity: ListingNewsResponse.ArticleResponse): Article {
        return Article(
            entity.source.name,
            entity.title,
            entity.description,
            entity.url,
            entity.image,
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                .parse(entity.publishedAt)!!
        )
    }

    override fun fromModel(model: Article): ListingNewsResponse.ArticleResponse {
        return ListingNewsResponse.ArticleResponse(
            ListingNewsResponse.Source(model.sourceName),
            model.title,
            model.description,
            model.url,
            model.urlToImage,
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(model.published)
        )
    }

    fun toEntity(entities: List<ListingNewsResponse.ArticleResponse>): List<ArticleEntity> {
        val result = mutableListOf<ArticleEntity>()
        for(entity in entities)
            result.add(toEntity(entity))
        return result
    }

    fun toEntity(entity: ListingNewsResponse.ArticleResponse): ArticleEntity {
        return ArticleEntity(
            sourceName = entity.source.name,
            title = entity.title,
            description = entity.description,
            url = entity.url,
            urlToImage = entity.image,
            published = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                .parse(entity.publishedAt)!!
        )
    }
}