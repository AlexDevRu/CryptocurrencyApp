package com.example.domain.repositories.remote

import com.example.domain.aliases.ArticleFlow

interface INewsApiRepository {
    suspend fun getNews(query: String): ArticleFlow
}