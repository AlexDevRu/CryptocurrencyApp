package com.example.domain.use_cases

import com.example.domain.repositories.remote.ICoinMarketCapRepository

class GetCurrencyInfoUseCase(private val repository: ICoinMarketCapRepository) {
    suspend operator fun invoke(id: Int) = repository.getCurrencyInfo(id)
}