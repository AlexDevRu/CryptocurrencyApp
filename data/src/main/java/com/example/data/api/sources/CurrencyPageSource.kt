package com.example.data.api.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.api.ApiConstants
import com.example.data.api.CoinMarketCapService
import com.example.data.mappers.CurrencyResponseMapper
import com.example.domain.models.Currency
import com.example.domain.models.CurrencyParameters
import retrofit2.HttpException
import java.io.IOException

class CurrencyPageSource(private val service: CoinMarketCapService,
                         private val parameters: CurrencyParameters
): PagingSource<Int, Currency>() {

    override fun getRefreshKey(state: PagingState<Int, Currency>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(ApiConstants.CURRENCY_PER_PAGE)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(ApiConstants.CURRENCY_PER_PAGE)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Currency> {
        val position = params.key ?: ApiConstants.STARTING_PAGE_INDEX

        return try {
            val currencyResult = service.getCurrencies(
                ApiConstants.CURRENCY_PER_PAGE,
                position,
                parameters.type,
                parameters.tag,
                parameters.priceMin,
                parameters.priceMax,
                parameters.marketCapMin,
                parameters.marketCapMax,
                parameters.sortType,
                parameters.sortDir
            ).data

            val query = parameters.searchQuery.lowercase()
            val currencyList = CurrencyResponseMapper.toModel(currencyResult).filter {
                it.name!!.lowercase().contains(query)
            }

            val nextKey = if (currencyList.isNullOrEmpty()) {
                null
            } else {
                position + params.loadSize
            }
            LoadResult.Page(
                data = currencyList,
                prevKey = if (position == ApiConstants.STARTING_PAGE_INDEX) null else position - params.loadSize,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}