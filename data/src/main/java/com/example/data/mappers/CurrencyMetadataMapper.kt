package com.example.data.mappers

import com.example.data.api.responses.CurrencyInfoValueResponse
import com.example.domain.mappers.IMapper
import com.example.domain.models.CurrencyMetadata

object CurrencyMetadataMapper: IMapper<CurrencyInfoValueResponse, CurrencyMetadata> {
    override fun toModel(entity: CurrencyInfoValueResponse): CurrencyMetadata {
        return CurrencyMetadata(
            entity.urls,
            entity.description
        )
    }

    override fun fromModel(model: CurrencyMetadata): CurrencyInfoValueResponse {
        return CurrencyInfoValueResponse(
            model.urls,
            model.description
        )
    }
}