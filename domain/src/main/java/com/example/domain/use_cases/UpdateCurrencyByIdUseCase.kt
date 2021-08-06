package com.example.domain.use_cases

import com.example.domain.repositories.remote.ICoinMarketCapRespository

class UpdateCurrencyByIdUseCase(private val repository: ICoinMarketCapRespository) {
    suspend operator fun invoke(id: Int) = repository.updateCurrencyById(id)
}