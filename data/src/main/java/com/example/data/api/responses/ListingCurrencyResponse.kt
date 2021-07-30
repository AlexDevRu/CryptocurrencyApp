package com.example.data.api.responses

data class ListingCurrencyResponse(
    val data: List<CurrencyResponse> = emptyList(),
    val status: Status
) {
    data class Status(
        val elapsed: Int? = null,
        val error_code: Int,
        val error_message: String? = null,
        val timestamp: String,
        val total_count: Int? = null
    )
}