package com.example.data.repositories.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.data.api.ApiConstants
import com.example.data.api.NewsApiService
import com.example.data.api.remote_mediators.CoinMarketCapRemoteMediator
import com.example.data.api.remote_mediators.NewsApiRemoteMediator
import com.example.data.api.sources.NewsPageSource
import com.example.data.database.CurrencyDatabase
import com.example.domain.aliases.ArticleFlow
import com.example.domain.repositories.remote.INewsApiRepository
import javax.inject.Inject

class NewsApiRepository @Inject constructor(
    private val service: NewsApiService,
    private val currencyDatabase: CurrencyDatabase
): INewsApiRepository {

    @ExperimentalPagingApi
    override suspend fun getNews(query: String): ArticleFlow {
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.NEWS_PER_PAGE,
                enablePlaceholders = false
            ),
            remoteMediator = NewsApiRemoteMediator(
                query,
                service,
                currencyDatabase
            ),
            pagingSourceFactory = {
                NewsPageSource(service, query)
            }
        ).flow
    }
}