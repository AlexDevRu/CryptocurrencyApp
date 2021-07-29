package com.example.domain.repositories.remote

import com.example.domain.aliases.CurrencyFlow
import com.example.domain.models.CurrencyParameters

interface ICoinMarketCapRespository {
    suspend fun getAllCurrencies(parameters: CurrencyParameters): CurrencyFlow
}