package com.example.data.api.remote_mediators

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.data.api.ApiConstants
import com.example.data.api.NewsApiService
import com.example.data.database.CurrencyDatabase
import com.example.data.database.entities.ArticleEntity
import com.example.data.database.entities.ArticleRemoteKeys
import com.example.data.mappers.toEntity
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class NewsApiRemoteMediator(
    private val query: String,
    private val service: NewsApiService,
    private val currencyDatabase: CurrencyDatabase
) : RemoteMediator<Int, ArticleEntity>() {

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ArticleEntity>): ArticleRemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { article ->
                // Get the remote keys of the last item retrieved
                currencyDatabase.articleRemoteKeysDao().remoteKeysArticleId(article.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ArticleEntity>): ArticleRemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { article ->
                // Get the remote keys of the first items retrieved
                currencyDatabase.articleRemoteKeysDao().remoteKeysArticleId(article.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ArticleEntity>
    ): ArticleRemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { articleId ->
                currencyDatabase.articleRemoteKeysDao().remoteKeysArticleId(articleId)
            }
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: ApiConstants.STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }

        try {
            val articles = service.getNewsByQuery(
                query,
                page,
                ApiConstants.NEWS_PER_PAGE
            ).articles

            Log.d("asd", "news api ${page}")

            val endOfPaginationReached = articles.size < state.config.pageSize

            currencyDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    currencyDatabase.articleRemoteKeysDao().clearRemoteKeys()
                    currencyDatabase.articleDao().clearAll()
                }
                val prevKey = if (page == ApiConstants.STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                Log.d("asd", "nextKey $nextKey, prevKey $prevKey")

                val dbEntities = articles.map { it.toEntity() }

                val keys = dbEntities.map {
                    ArticleRemoteKeys(articleId = it.id, prevKey = prevKey, nextKey = nextKey)
                }

                currencyDatabase.articleRemoteKeysDao().insertAll(keys)
                currencyDatabase.articleDao().insertAll(dbEntities)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
}