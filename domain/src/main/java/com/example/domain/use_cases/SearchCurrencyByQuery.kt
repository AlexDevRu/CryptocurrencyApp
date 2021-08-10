package com.example.domain.use_cases

import com.example.domain.models.CurrencyParameters
import com.example.domain.repositories.remote.ICoinMarketCapRespository

class SearchCurrencyByQuery(private val repository: ICoinMarketCapRespository) {
    suspend operator fun invoke(parameters: CurrencyParameters) = repository.searchCurrencyByQuery(parameters)
}