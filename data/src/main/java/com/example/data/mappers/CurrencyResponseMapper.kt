package com.example.data.mappers

import com.example.data.api.responses.CurrencyResponse
import com.example.data.database.entities.CurrencyEntity
import com.example.domain.mappers.IMapper
import com.example.domain.models.Currency
import java.text.SimpleDateFormat
import java.util.*

object CurrencyResponseMapper: IMapper<CurrencyResponse, Currency> {
    override fun toModel(entity: CurrencyResponse): Currency {
        return Currency(
            entity.id,
            entity.name,
            entity.symbol,
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                .parse(entity.last_updated)!!,
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
            entity.symbol,
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                .parse(entity.last_updated)!!
        )
    }
}