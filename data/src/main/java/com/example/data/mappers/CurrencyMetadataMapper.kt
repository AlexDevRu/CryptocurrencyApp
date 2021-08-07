package com.example.data.mappers

import com.example.data.api.responses.CurrencyInfoValueResponse
import com.example.domain.models.CurrencyMetadata

fun CurrencyInfoValueResponse.toModel(): CurrencyMetadata {
    return CurrencyMetadata(urls, description)
}
