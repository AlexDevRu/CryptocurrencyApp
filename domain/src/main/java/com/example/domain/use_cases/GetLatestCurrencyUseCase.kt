package com.example.domain.use_cases

import com.example.domain.repositories.remote.ICoinMarketCapRepository

class GetLatestCurrencyUseCase(private val repository: ICoinMarketCapRepository) {
    suspend operator fun invoke() = repository.getLatestCurrency()
}