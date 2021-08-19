package com.example.domain.use_cases

import com.example.domain.models.Currency
import com.example.domain.repositories.remote.ICoinMarketCapRepository

class GetUpdatedCurrencyUseCase(private val repository: ICoinMarketCapRepository) {
    suspend operator fun invoke(currency: Currency) = repository.getUpdatedCurrency(currency)
}