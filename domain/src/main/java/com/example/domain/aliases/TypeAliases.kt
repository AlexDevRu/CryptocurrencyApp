package com.example.domain.aliases

import androidx.paging.PagingData
import com.example.domain.models.Currency
import kotlinx.coroutines.flow.Flow

typealias CurrencyFlow = Flow<PagingData<Currency>>