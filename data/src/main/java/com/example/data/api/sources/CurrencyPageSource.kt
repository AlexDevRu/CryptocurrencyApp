package com.example.data.api.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.api.ApiConstants
import com.example.data.api.ApiConstants.STARTING_PAGE_INDEX
import com.example.data.api.CoinMarketCapService
import com.example.data.mappers.CurrencyResponseMapper
import com.example.domain.models.Currency
import retrofit2.HttpException
import java.io.IOException

class CurrencyPageSource(private val service: CoinMarketCapService,
                       //private val onResponse: suspend (response: SearchItem) -> Unit
): PagingSource<Int, Currency>() {

    override fun getRefreshKey(state: PagingState<Int, Currency>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(ApiConstants.PER_PAGE)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(ApiConstants.PER_PAGE)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Currency> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val currencyResult = service.getCurrencies(ApiConstants.PER_PAGE, position).data

            val currencyList = CurrencyResponseMapper.toModel(currencyResult)

            val nextKey = if (currencyList.isNullOrEmpty()) {
                null
            } else {
                position + params.loadSize
            }
            LoadResult.Page(
                data = currencyList,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - params.loadSize,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}