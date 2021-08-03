package com.example.data.mappers

import com.example.data.api.responses.CurrencyResponse
import com.example.data.database.entities.CurrencyEntity
import com.example.domain.mappers.IMapper
import com.example.domain.models.Currency

object CurrencyResponseMapper: IMapper<CurrencyResponse, Currency> {
    override fun toModel(entity: CurrencyResponse): Currency {
        return Currency(
            entity.id,
            entity.name,
            entity.symbol,
            entity.quote
        )
    }

    override fun fromModel(model: Currency): CurrencyResponse {
        TODO("Not yet implemented")
    }

    fun toDbEntity(entity: CurrencyResponse): CurrencyEntity {
        return CurrencyEntity(
            entity.id,
            entity.name,
            entity.symbol
        )
    }
}