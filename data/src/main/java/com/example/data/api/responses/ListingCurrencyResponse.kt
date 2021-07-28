package com.example.data.api.responses

data class ListingCurrencyResponse(
    val data: List<CurrencyResponse> = emptyList(),
    val status: Status
) {
    data class Status(
        val credit_count: Int,
        val elapsed: Int,
        val error_code: Int,
        val error_message: Any,
        val notice: Any,
        val timestamp: String,
        val total_count: Int
    )
}