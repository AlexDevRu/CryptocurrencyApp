package com.example.domain.repositories.remote

import com.example.domain.aliases.CurrencyFlow

interface ICoinMarketCapRespository {
    suspend fun getAllCurrencies(): CurrencyFlow
}