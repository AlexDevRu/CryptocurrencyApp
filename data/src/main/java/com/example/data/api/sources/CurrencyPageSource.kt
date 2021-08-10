package com.example.data.api.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.api.ApiConstants
import com.example.data.api.CoinMarketCapService
import com.example.data.api.responses.CurrencyResponse
import com.example.data.mappers.toModel
import com.example.domain.models.Currency
import com.example.domain.models.CurrencyParameters
import retrofit2.HttpException
import java.io.IOException

class CurrencyPageSource(private val service: CoinMarketCapService,
                         private val parameters: CurrencyParameters
): PagingSource<Int, Currency>() {

    override fun getRefreshKey(state: PagingState<Int, Currency>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Currency> {
        val position = params.key ?: ApiConstants.STARTING_PAGE_INDEX

        return try {
            val currencyResult = service.getCurrencies(
                ApiConstants.CURRENCY_PER_PAGE,
                ((position - 1) * params.loadSize) + 1,
                parameters.type,
                parameters.tag,
                parameters.priceMin,
                parameters.priceMax,
                parameters.marketCapMin,
                parameters.marketCapMax,
                parameters.sortType,
                parameters.sortDir
            ).data

            val nextKey = if (currencyResult.isNullOrEmpty()) {
                null
            } else {
                position + params.loadSize
            }
            LoadResult.Page(
                data = currencyResult.map { it.toModel() },
                prevKey = if (position == ApiConstants.STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}