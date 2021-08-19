package com.example.domain.models

import java.util.*

data class FavoriteCurrency(
    val id: UUID = UUID.randomUUID(),
    val addedToFavorite: Date = Date(),
    val currencyId: Int
)
