package com.example.data.api.responses.news

data class ListingNewsResponse(
    val articles: List<ArticleResponse> = emptyList()
) {
    data class ArticleResponse(
        val source: Source,
        val title: String,
        val description: String,
        val url: String,
        val image: String,
        val publishedAt: String,
    )
    data class Source(val name: String)
}