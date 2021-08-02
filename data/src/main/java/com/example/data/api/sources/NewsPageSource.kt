package com.example.data.api.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.api.ApiConstants
import com.example.data.api.ApiConstants.STARTING_PAGE_INDEX
import com.example.data.api.NewsApiService
import com.example.data.mappers.ArticleResponseMapper
import com.example.domain.models.news.Article
import retrofit2.HttpException
import java.io.IOException

class NewsPageSource(private val service: NewsApiService,
                     private val query: String
): PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = service.getNewsByQuery(query, position, params.loadSize).articles

            val photos = ArticleResponseMapper.toModel(response)

            val nextKey = if (photos.isNullOrEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / ApiConstants.NEWS_PER_PAGE)
            }
            LoadResult.Page(
                data = photos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
