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
import com.example.data.mappers.toCurrencyWithQuotes
import com.example.domain.models.CurrencyParameters
import com.example.domain.repositories.remote.IFirebaseRepository
import retrofit2.HttpException
import java.io.IOException


@ExperimentalPagingApi
class CoinMarketCapRemoteMediator(
    private val parameters: CurrencyParameters,
    private val service: CoinMarketCapService,
    private val firebaseRepository: IFirebaseRepository,
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

            var currencies = service.getCurrencies(
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

            val ids = firebaseRepository.getFavorite()

            if(parameters.searchQuery.isNotEmpty())
                currencies = currencies.filter { it.name.lowercase().contains(parameters.searchQuery.trim().lowercase()) }

            Log.w("asd", "favorite ids ${ids.size}")
            Log.w("asd", "currencies ${currencies}")

            val endOfPaginationReached = currencies.isEmpty()

            currencyDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    currencyDatabase.currencyRemoteKeysDao().clearRemoteKeys()
                    currencyDatabase.currencyDao().clearAll()
                }
                val prevKey = if (page == ApiConstants.STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                Log.e("asd", "nextKey $nextKey, prevKey $prevKey")
                val keys = currencies.map {
                    CurrencyRemoteKeys(currencyId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                currencyDatabase.currencyRemoteKeysDao().insertAll(keys)

                val entityList = mutableListOf<CurrencyWithQuotes>()
                for(currency in currencies) {

                    val favoriteItem = ids.find { it.currencyId == currency.id }

                    val currencyWithQuotes = currency.toCurrencyWithQuotes(favoriteItem?.addedToFavorite)
                    currencyDatabase.currencyDao().insert(currencyWithQuotes)

                    entityList.add(currencyWithQuotes)
                }
                currencyDatabase.currencyDao().insert(entityList)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
}