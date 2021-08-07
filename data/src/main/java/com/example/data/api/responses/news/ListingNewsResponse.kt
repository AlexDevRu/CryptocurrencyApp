package com.example.data.api.responses.news

data class ListingNewsResponse(
    val articles: List<ArticleResponse> = emptyList()
) {
    data class Source(val name: String)
}