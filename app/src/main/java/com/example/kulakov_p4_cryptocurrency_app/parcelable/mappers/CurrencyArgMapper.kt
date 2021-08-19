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
            entity.lastUpdated,
            entity.cmcRank,
            entity.circulatingSupply,
            entity.dateAdded,
            entity.maxSupply,
            entity.marketPairs,
            entity.tags,
            entity.totalSupply,
            entity.addedToFavorite,
            entity.quote.mapValues { entry -> QuoteItemMapper.toModel(entry.value) }
        )
    }

    override fun fromModel(model: Currency): CurrencyArg {
        return CurrencyArg(
            model.id,
            model.name,
            model.symbol,
            model.lastUpdated,
            model.cmcRank,
            model.circulatingSupply,
            model.dateAdded,
            model.maxSupply,
            model.marketPairs,
            model.tags,
            model.totalSupply,
            model.addedToFavorite,
            model.quote.mapValues { entry -> QuoteItemMapper.fromModel(entry.value) }
        )
    }
}