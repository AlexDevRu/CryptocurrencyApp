package com.example.kulakov_p4_cryptocurrency_app.parcelable.mappers

import com.example.domain.mappers.IMapper
import com.example.domain.models.QuoteItem
import com.example.kulakov_p4_cryptocurrency_app.parcelable.QuoteItemArg

object QuoteItemMapper: IMapper<QuoteItemArg, QuoteItem> {
    override fun toModel(entity: QuoteItemArg): QuoteItem {
        return QuoteItem(
            entity.last_updated,
            entity.market_cap,
            entity.percent_change_1h,
            entity.percent_change_24h,
            entity.percent_change_30d,
            entity.percent_change_60d,
            entity.percent_change_7d,
            entity.percent_change_90d,
            entity.price,
            entity.volume_24h
        )
    }

    override fun fromModel(model: QuoteItem): QuoteItemArg {
        return QuoteItemArg(
            model.last_updated,
            model.market_cap,
            model.percent_change_1h,
            model.percent_change_24h,
            model.percent_change_30d,
            model.percent_change_60d,
            model.percent_change_7d,
            model.percent_change_90d,
            model.price,
            model.volume_24h
        )
    }
}