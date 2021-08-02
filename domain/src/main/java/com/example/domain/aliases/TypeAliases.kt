package com.example.domain.aliases

import androidx.paging.PagingData
import com.example.domain.models.Currency
import com.example.domain.models.news.Article
import kotlinx.coroutines.flow.Flow

typealias CurrencyFlow = Flow<PagingData<Currency>>
typealias ArticleFlow = Flow<PagingData<Article>>