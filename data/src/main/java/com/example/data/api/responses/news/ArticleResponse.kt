package com.example.data.api.responses.news

data class ArticleResponse(
    val source: ListingNewsResponse.Source,
    val title: String,
    val description: String,
    val url: String,
    val image: String,
    val publishedAt: String,
)