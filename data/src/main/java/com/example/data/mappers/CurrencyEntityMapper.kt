package com.example.data.mappers

import com.example.data.database.entities.CurrencyEntity
import com.example.domain.mappers.IMapper
import com.example.domain.models.Currency

object CurrencyEntityMapper: IMapper<CurrencyEntity, Currency> {
    override fun toModel(entity: CurrencyEntity): Currency {
        return Currency(
            entity.id,
            entity.name,
            entity.symbol,
            entity.last_updated,
            quote = mapOf()
        )
    }

    override fun fromModel(model: Currency): CurrencyEntity {
        return CurrencyEntity(
            model.id,
            model.name,
            model.symbol,
            model.last_updated
        )
    }
}