package com.example.domain.use_cases.firebase

import com.example.domain.models.Currency
import com.example.domain.repositories.remote.ICoinMarketCapRepository
import com.example.domain.repositories.remote.IFirebaseRepository

class SaveFavoriteCurrencyUseCase(
    private val firebaseRepository: IFirebaseRepository,
    private val coinMarketCapRepository: ICoinMarketCapRepository
) {
    suspend operator fun invoke(currency: Currency) {
        coinMarketCapRepository.updateCurrency(currency)
        firebaseRepository.saveFavorite(currency)
    }
}