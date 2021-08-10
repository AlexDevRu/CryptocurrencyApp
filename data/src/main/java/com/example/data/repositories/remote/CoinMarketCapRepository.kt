package com.example.data.repositories.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.data.api.ApiConstants
import com.example.data.api.CoinMarketCapService
import com.example.data.api.remote_mediators.CoinMarketCapRemoteMediator
import com.example.data.database.CurrencyDatabase
import com.example.data.database.entities.CurrencyWithQuotes
import com.example.data.mappers.toEntity
import com.example.data.mappers.toModel
import com.example.domain.aliases.CurrencyFlow
import com.example.domain.common.Result
import com.example.domain.models.Currency
import com.example.domain.models.CurrencyMetadata
import com.example.domain.models.CurrencyParameters
import com.example.domain.repositories.remote.ICoinMarketCapRespository
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CoinMarketCapRepository @Inject constructor(
    private val service: CoinMarketCapService,
    private val currencyDatabase: CurrencyDatabase
): ICoinMarketCapRespository {

    @ExperimentalPagingApi
    override suspend fun getAllCurrencies(parameters: CurrencyParameters): CurrencyFlow {
        return createPagerFlow(ApiConstants.CURRENCY_PER_PAGE, parameters)
    }

    override suspend fun getCurrencyInfo(id: Int): Result<CurrencyMetadata> {
        return try {
            val strId = id.toString()
            val response = service.getCurrencyInfo(strId)
            val result = response.data[strId]!!.toModel()
            Result.Success(result)
        } catch (exception: IOException) {
            Result.Failure(exception)
        } catch (exception: HttpException) {
            Result.Failure(exception)
        } catch (exception: Exception) {
            Result.Failure(exception)
        }
    }

    override suspend fun getLatestCurrencyUseCase(): Result<Currency> {
        try {
            val currencyInDb = currencyDatabase.currencyDao().getLatestCurrency()
            if(currencyInDb != null)
                return Result.Success(currencyInDb.toModel())

            val response = service.getLatestCurrency()
            return Result.Success(response.data.first().toModel())
        } catch (exception: IOException) {
            return Result.Failure(exception)
        } catch (exception: HttpException) {
            return Result.Failure(exception)
        } catch (exception: Exception) {
            return Result.Failure(exception)
        }
    }

    override suspend fun updateCurrencyById(id: Int): Result<Currency> {
        return try {
            val response = service.getCurrencyById(id.toString()).data[id.toString()]!!
            val currencyWithQuotes = CurrencyWithQuotes(
                response.toEntity(),
                response.quote.map { it.value.toEntity(it.key) }
            )
            currencyDatabase.currencyDao().updateCurrency(currencyWithQuotes)
            Result.Success(currencyWithQuotes.toModel())
        } catch (exception: IOException) {
            return getCurrencyFromDb(id)
        } catch (exception: HttpException) {
            return getCurrencyFromDb(id)
        } catch (exception: Exception) {
            Result.Failure(exception)
        }
    }

    private suspend fun getCurrencyFromDb(id: Int): Result<Currency> {
        return try {
            val currencyWithQuotes = currencyDatabase.currencyDao().getCurrencyById(id)!!
            Result.Success(currencyWithQuotes.toModel())
        } catch (e: IOException) {
            Result.Failure(e)
        }
    }

    @ExperimentalPagingApi
    private fun createPagerFlow(pageSize: Int, parameters: CurrencyParameters): CurrencyFlow {
        val dbQuery = "%${parameters.searchQuery.trim().lowercase().replace(' ', '%')}%"
        val tag = if(parameters.tag == "all") "%" else "%${parameters.tag.lowercase().replace(' ', '%')}%"
        val sortType = when(parameters.sortType) {
            "date_added" -> "dateAdded"
            "market_cap" -> "marketCap"
            else -> parameters.sortType
        }

        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false,
                prefetchDistance = 15,
                initialLoadSize = pageSize
            ),
            remoteMediator = CoinMarketCapRemoteMediator(
                parameters,
                service,
                currencyDatabase
            ),
            pagingSourceFactory = {
                /*currencyDatabase.currencyDao().getCurrencies(
                    dbQuery,
                    parameters.priceMin, parameters.priceMax,
                    parameters.marketCapMin, parameters.marketCapMax
                )*/
                currencyDatabase.currencyDao().getAll(
                    SimpleSQLiteQuery(
                        """select * from currencies, quotes where 
                        currencyId=currencies.id and
                        name like ? and
                        (price between ? and ?) and
                        (marketCap between ? and ?) and
                        tags like ?
                    """.trimIndent() + " order by ${sortType} ${parameters.sortDir}",
                        arrayOf(dbQuery, parameters.priceMin.toString(), parameters.priceMax.toString(),
                            parameters.marketCapMin.toString(), parameters.marketCapMax.toString(), tag),
                    )
                )
            }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toModel()
            }
        }
    }

    @ExperimentalPagingApi
    override suspend fun searchCurrencyByQuery(parameters: CurrencyParameters): CurrencyFlow {
        return createPagerFlow(5000, parameters)
    }
}