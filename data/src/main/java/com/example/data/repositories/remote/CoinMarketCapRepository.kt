package com.example.data.repositories.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.example.data.api.ApiConstants
import com.example.data.api.CoinMarketCapService
import com.example.data.api.remote_mediators.CoinMarketCapRemoteMediator
import com.example.data.database.CurrencyDatabase
import com.example.data.mappers.CurrencyEntityMapper
import com.example.data.mappers.CurrencyMetadataMapper
import com.example.data.mappers.CurrencyResponseMapper
import com.example.data.mappers.CurrencyWithQuotesMapper
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
        val dbQuery = "%${parameters.searchQuery.lowercase().replace(' ', '%')}%"
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.CURRENCY_PER_PAGE,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            remoteMediator = CoinMarketCapRemoteMediator(
                parameters,
                service,
                currencyDatabase
            ),
            pagingSourceFactory = {
                currencyDatabase.currencyDao().getCurrencies(
                    dbQuery,
                    parameters.priceMin, parameters.priceMax,
                    parameters.marketCapMin, parameters.marketCapMax
                )
            }
        ).flow.map { pagingData ->
            pagingData.map {
                CurrencyWithQuotesMapper.toModel(it)
            }
        }
    }

    override suspend fun getCurrencyInfo(id: Int): Result<CurrencyMetadata> {
        return try {
            val response = service.getCurrencyInfo(id.toString())
            val result = CurrencyMetadataMapper.toModel(response.data[id.toString()]!!)
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
                return Result.Success(CurrencyWithQuotesMapper.toModel(currencyInDb))

            val response = service.getLatestCurrency()
            return Result.Success(CurrencyResponseMapper.toModel(response.data.first()))
        } catch (exception: IOException) {
            return Result.Failure(exception)
        } catch (exception: HttpException) {
            return Result.Failure(exception)
        } catch (exception: Exception) {
            return Result.Failure(exception)
        }
    }
}