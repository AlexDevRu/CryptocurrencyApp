package com.example.data.api.remote_mediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.data.api.ApiConstants
import com.example.data.api.CoinMarketCapService
import com.example.data.database.CurrencyDatabase
import com.example.data.database.entities.CurrencyRemoteKeys
import com.example.data.mappers.CurrencyResponseMapper
import com.example.data.mappers.QuoteItemMapper
import com.example.domain.models.Currency
import com.example.domain.models.CurrencyParameters
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CoinMarketCapRemoteMediator(
    private val parameters: CurrencyParameters,
    private val service: CoinMarketCapService,
    private val currencyDatabase: CurrencyDatabase
) : RemoteMediator<Int, Currency>() {

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Currency>): CurrencyRemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { currency ->
                // Get the remote keys of the last item retrieved
                currencyDatabase.currencyRemoteKeysDao().remoteKeysCurrencyId(currency.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Currency>): CurrencyRemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { currency ->
                // Get the remote keys of the first items retrieved
                currencyDatabase.currencyRemoteKeysDao().remoteKeysCurrencyId(currency.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Currency>
    ): CurrencyRemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { currencyId ->
                currencyDatabase.currencyRemoteKeysDao().remoteKeysCurrencyId(currencyId)
            }
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Currency>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(ApiConstants.CURRENCY_PER_PAGE) ?: ApiConstants.STARTING_PAGE_INDEX
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
            val currencies = service.getCurrencies(
                ApiConstants.CURRENCY_PER_PAGE,
                page,
                parameters.type,
                parameters.tag,
                parameters.priceMin,
                parameters.priceMax,
                parameters.marketCapMin,
                parameters.marketCapMax,
                parameters.sortType,
                parameters.sortDir
            ).data

            val endOfPaginationReached = currencies.isEmpty()

            currencyDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    currencyDatabase.currencyRemoteKeysDao().clearRemoteKeys()
                    currencyDatabase.currencyDao().clearAll()
                }
                val prevKey = if (page == ApiConstants.STARTING_PAGE_INDEX) null else page - ApiConstants.CURRENCY_PER_PAGE
                val nextKey = if (endOfPaginationReached) null else page + ApiConstants.CURRENCY_PER_PAGE
                val keys = currencies.map {
                    CurrencyRemoteKeys(currencyId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                currencyDatabase.currencyRemoteKeysDao().insertAll(keys)
                for(currency in currencies) {
                    val quotes = QuoteItemMapper.fromModel(currency.quote)
                    currencyDatabase.currencyDao().insertAll(CurrencyResponseMapper.toDbEntity(currency), quotes)
                }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
}