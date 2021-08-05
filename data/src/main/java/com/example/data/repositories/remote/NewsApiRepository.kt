package com.example.data.repositories.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.data.api.ApiConstants
import com.example.data.api.NewsApiService
import com.example.data.api.remote_mediators.NewsApiRemoteMediator
import com.example.data.database.CurrencyDatabase
import com.example.domain.aliases.ArticleFlow
import com.example.domain.repositories.remote.INewsApiRepository
import javax.inject.Inject
import androidx.paging.map
import com.example.data.mappers.ArticleMapper
import kotlinx.coroutines.flow.map

class NewsApiRepository @Inject constructor(
    private val service: NewsApiService,
    private val currencyDatabase: CurrencyDatabase
): INewsApiRepository {

    @ExperimentalPagingApi
    override suspend fun getNews(query: String): ArticleFlow {
        val dbQuery = "%${query.replace(' ', '%')}%"
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.NEWS_PER_PAGE,
                enablePlaceholders = false,
                initialLoadSize = ApiConstants.NEWS_PER_PAGE * 2,
                prefetchDistance = 1
            ),
            remoteMediator = NewsApiRemoteMediator(
                query,
                service,
                currencyDatabase
            ),
            pagingSourceFactory = {
                currencyDatabase.articleDao().getArticlesByQuery(dbQuery)
            }
        ).flow.map { pagingData ->
            pagingData.map {
                ArticleMapper.toModel(it)
            }
        }
    }
}