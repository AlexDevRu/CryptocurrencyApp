package com.example.domain.use_cases

import com.example.domain.common.Result
import com.example.domain.models.Currency
import com.example.domain.repositories.remote.ICoinMarketCapRepository

class GetFavoriteCurrenciesUseCase(
    private val coinMarketCapRepository: ICoinMarketCapRepository
) {
    suspend operator fun invoke(query: String): Result<List<Currency>> {
        return coinMarketCapRepository.getFavoriteCurrencies(query)
    }
}