package com.example.domain.use_cases

import com.example.domain.models.CurrencyParameters
import com.example.domain.repositories.remote.ICoinMarketCapRespository

class GetCurrenciesUseCase(private val repository: ICoinMarketCapRespository) {
    suspend operator fun invoke(parameters: CurrencyParameters) = repository.getAllCurrencies(parameters)
}