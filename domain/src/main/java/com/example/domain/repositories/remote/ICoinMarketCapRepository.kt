package com.example.domain.repositories.remote

import com.example.domain.aliases.CurrencyFlow
import com.example.domain.models.CurrencyMetadata
import com.example.domain.models.CurrencyParameters
import com.example.domain.common.Result
import com.example.domain.models.Currency

interface ICoinMarketCapRepository {
    suspend fun getAllCurrencies(parameters: CurrencyParameters): CurrencyFlow
    suspend fun getCurrencyInfo(id: Int): Result<CurrencyMetadata>
    suspend fun getLatestCurrency(): Result<Currency>
    suspend fun getUpdatedCurrency(currency: Currency): Result<Currency>
    suspend fun searchCurrencyByQuery(parameters: CurrencyParameters): CurrencyFlow
    suspend fun getCurrenciesByIds(ids: List<Int>): Result<List<Currency>>
    suspend fun updateCurrency(currency: Currency): Result<Unit>
    suspend fun getFavoriteCurrencies(query: String): Result<List<Currency>>
}