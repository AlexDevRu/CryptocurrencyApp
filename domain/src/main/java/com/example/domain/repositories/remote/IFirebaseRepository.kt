package com.example.domain.repositories.remote

import com.example.domain.common.Result
import com.example.domain.models.Currency
import com.example.domain.models.FavoriteCurrency

interface IFirebaseRepository {
    suspend fun saveFavorite(currency: Currency): Result<Unit>
    suspend fun deleteFavorite(id: Int): Result<Unit>
    suspend fun getFavorite(): List<FavoriteCurrency>
}