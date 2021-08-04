package com.example.kulakov_p4_cryptocurrency_app.parcelable.mappers

import com.example.domain.mappers.IMapper
import com.example.domain.models.Currency
import com.example.kulakov_p4_cryptocurrency_app.parcelable.CurrencyArg

object CurrencyArgMapper: IMapper<CurrencyArg, Currency> {
    override fun toModel(entity: CurrencyArg): Currency {
        return Currency(
            entity.id,
            entity.name,
            entity.symbol,
            entity.last_updated,
            entity.quote.mapValues { entry -> QuoteItemMapper.toModel(entry.value) }
        )
    }

    override fun fromModel(model: Currency): CurrencyArg {
        return CurrencyArg(
            model.id,
            model.name,
            model.symbol,
            model.last_updated,
            model.quote.mapValues { entry -> QuoteItemMapper.fromModel(entry.value) }
        )
    }
}