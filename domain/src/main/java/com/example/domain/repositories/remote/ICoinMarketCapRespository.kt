package com.example.domain.repositories.remote

import com.example.domain.aliases.CurrencyFlow
import com.example.domain.models.CurrencyMetadata
import com.example.domain.models.CurrencyParameters
import com.example.domain.common.Result
import com.example.domain.models.Currency

interface ICoinMarketCapRespository {
    suspend fun getAllCurrencies(parameters: CurrencyParameters): CurrencyFlow
    suspend fun getCurrencyInfo(id: Int): Result<CurrencyMetadata>
    suspend fun getLatestCurrencyUseCase(): Result<Currency>
}