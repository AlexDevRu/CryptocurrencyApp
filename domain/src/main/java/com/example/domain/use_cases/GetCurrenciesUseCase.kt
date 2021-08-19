package com.example.domain.use_cases

import com.example.domain.models.CurrencyParameters
import com.example.domain.repositories.remote.ICoinMarketCapRepository

class GetCurrenciesUseCase(
    private val coinMarketCapRepository: ICoinMarketCapRepository
) {
    suspend operator fun invoke(parameters: CurrencyParameters = CurrencyParameters())
    = coinMarketCapRepository.getAllCurrencies(parameters)
}