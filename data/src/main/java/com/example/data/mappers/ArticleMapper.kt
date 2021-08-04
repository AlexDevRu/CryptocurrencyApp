package com.example.data.mappers

import com.example.data.database.entities.ArticleEntity
import com.example.domain.mappers.IMapper
import com.example.domain.models.news.Article

object ArticleMapper: IMapper<ArticleEntity, Article> {
    override fun toModel(entity: ArticleEntity): Article {
        return Article(
            entity.sourceName,
            entity.title,
            entity.description,
            entity.url,
            entity.urlToImage,
            entity.published
        )
    }

    override fun fromModel(model: Article): ArticleEntity {
        return ArticleEntity(
            sourceName = model.sourceName,
            title = model.title,
            description = model.description,
            url = model.url,
            urlToImage = model.urlToImage,
            published = model.published
        )
    }
}