package com.example.domain.use_cases

import com.example.domain.repositories.remote.ICoinMarketCapRespository

class GetLatestCurrencyUseCase(private val repository: ICoinMarketCapRespository) {
    suspend operator fun invoke() = repository.getLatestCurrencyUseCase()
}