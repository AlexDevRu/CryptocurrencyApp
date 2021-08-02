package com.example.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.data.api.ApiConstants
import com.example.data.api.CoinMarketCapService
import com.example.data.api.sources.CurrencyPageSource
import com.example.data.mappers.CurrencyMetadataMapper
import com.example.domain.aliases.CurrencyFlow
import com.example.domain.common.Result
import com.example.domain.models.CurrencyMetadata
import com.example.domain.models.CurrencyParameters
import com.example.domain.repositories.remote.ICoinMarketCapRespository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CoinMarketCapRepository @Inject constructor(
    private val service: CoinMarketCapService,
): ICoinMarketCapRespository {
    override suspend fun getAllCurrencies(parameters: CurrencyParameters): CurrencyFlow {
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.CURRENCY_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CurrencyPageSource(service, parameters)
            }
        ).flow
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
}