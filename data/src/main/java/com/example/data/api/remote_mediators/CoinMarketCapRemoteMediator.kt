package com.example.data.api.remote_mediators

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.data.api.ApiConstants
import com.example.data.api.CoinMarketCapService
import com.example.data.database.CurrencyDatabase
import com.example.data.database.entities.CurrencyRemoteKeys
import com.example.data.database.entities.CurrencyWithQuotes
import com.example.data.mappers.toEntity
import com.example.domain.models.CurrencyParameters
import retrofit2.HttpException
import java.io.IOException


@ExperimentalPagingApi
class CoinMarketCapRemoteMediator(
    private val parameters: CurrencyParameters,
    private val service: CoinMarketCapService,
    private val currencyDatabase: CurrencyDatabase
) : RemoteMediator<Int, CurrencyWithQuotes>() {

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CurrencyWithQuotes>): CurrencyRemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { currency ->
                // Get the remote keys of the last item retrieved
                currencyDatabase.currencyRemoteKeysDao().remoteKeysCurrencyId(currency.currency.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CurrencyWithQuotes>): CurrencyRemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { currency ->
                // Get the remote keys of the first items retrieved
                currencyDatabase.currencyRemoteKeysDao().remoteKeysCurrencyId(currency.currency.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, CurrencyWithQuotes>
    ): CurrencyRemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.currency?.id?.let { currencyId ->
                currencyDatabase.currencyRemoteKeysDao().remoteKeysCurrencyId(currencyId)
            }
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, CurrencyWithQuotes>): MediatorResult {
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

        Log.w("asd", "page = $page, pageSize = ${state.config.pageSize}, start = ${(page - 1) * state.config.pageSize + 1}")

        try {
            val currencies = service.getCurrencies(
                state.config.pageSize,
                ((page - 1) * state.config.pageSize) + 1,
                parameters.type,
                parameters.tag,
                parameters.priceMin,
                parameters.priceMax,
                parameters.marketCapMin,
                parameters.marketCapMax,
                parameters.sortType,
                parameters.sortDir
            ).data

            /*var currencies = mutableListOf<CurrencyResponse>()
            for(i in 1..1000) currencies.add(
                CurrencyResponse(i.toDouble(),
                    i,
                    "2018-08-09T22:53:32.000Z",
                    i,
                    "2018-08-09T22:53:32.000Z",
                    i.toDouble(),
                    "name$i",
                    1,
                    9,
                    mapOf("USD" to QuoteItem(
                        "2018-08-09T22:53:32.000Z",
                        i.toDouble(),
                        (i + 1).toDouble(),
                        (i + 2).toDouble(),
                        (i + 3).toDouble(),
                        (i + 4).toDouble(),
                        (i + 5).toDouble(),
                        (i + 6).toDouble(),
                        (i + 7).toDouble(),
                        (i + 8).toDouble()
                    )
                    ),
                    "slug",
                    "symbol_$i",
                    listOf("kjf"),
                    (i).toDouble()
                )
            )
            val start = (page - 1) * state.config.pageSize
            val end = start + state.config.pageSize
            if(start >= currencies.size)
                currencies = mutableListOf()
            else if(end < currencies.size)
                currencies = currencies.subList(start, end)
            else
                currencies = currencies.subList(start, currencies.size)

            delay(4000)*/

            Log.w("asd", "currencies ${currencies}")
            Log.w("asd", "currencies quote ${currencies[0].quote}")

            val endOfPaginationReached = currencies.size < state.config.pageSize

            currencyDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    currencyDatabase.currencyRemoteKeysDao().clearRemoteKeys()
                    currencyDatabase.currencyDao().clearAll()
                }
                val prevKey = if (page == ApiConstants.STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + (state.config.pageSize / ApiConstants.CURRENCY_PER_PAGE)
                Log.e("asd", "nextKey $nextKey, prevKey $prevKey")
                val keys = currencies.map {
                    CurrencyRemoteKeys(currencyId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                currencyDatabase.currencyRemoteKeysDao().insertAll(keys)

                val entityList = mutableListOf<CurrencyWithQuotes>()
                for(currency in currencies) {
                    val quotes = currency.quote.map { it.value.toEntity(it.key) }
                    val currencyWithQuotes = CurrencyWithQuotes(
                        currency.toEntity(),
                        quotes
                    )
                    entityList.add(currencyWithQuotes)
                }
                currencyDatabase.currencyDao().insertAll(entityList)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
}