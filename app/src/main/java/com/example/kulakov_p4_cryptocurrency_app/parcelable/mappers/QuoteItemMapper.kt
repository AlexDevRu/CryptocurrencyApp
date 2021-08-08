package com.example.kulakov_p4_cryptocurrency_app.parcelable.mappers

import com.example.domain.mappers.IMapper
import com.example.domain.models.QuoteItem
import com.example.kulakov_p4_cryptocurrency_app.parcelable.QuoteItemArg

object QuoteItemMapper: IMapper<QuoteItemArg, QuoteItem> {
    override fun toModel(entity: QuoteItemArg): QuoteItem {
        return QuoteItem(
            entity.marketCap,
            entity.percentChange1h,
            entity.percentChange24h,
            entity.percentChange7d,
            entity.percentChange30d,
            entity.percentChange60d,
            entity.percentChange90d,
            entity.price,
            entity.volume24h
        )
    }

    override fun fromModel(model: QuoteItem): QuoteItemArg {
        return QuoteItemArg(
            model.marketCap,
            model.percentChange1h,
            model.percentChange24h,
            model.percentChange7d,
            model.percentChange30d,
            model.percentChange60d,
            model.percentChange90d,
            model.price,
            model.volume24h
        )
    }
}