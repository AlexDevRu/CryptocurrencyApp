package com.example.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.data.api.ApiConstants
import com.example.data.api.CoinMarketCapService
import com.example.data.api.sources.CurrencyPageSource
import com.example.domain.aliases.CurrencyFlow
import com.example.domain.repositories.remote.ICoinMarketCapRespository
import javax.inject.Inject

class CoinMarketCapRepository @Inject constructor(
    private val service: CoinMarketCapService,
): ICoinMarketCapRespository {
    override suspend fun getAllCurrencies(): CurrencyFlow {
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstants.PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CurrencyPageSource(service)
            }
        ).flow
    }
}