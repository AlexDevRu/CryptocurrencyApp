package com.example.data.api

import com.example.data.api.responses.ListingCurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CoinMarketCapService {
    @Headers("X-CMC_PRO_API_KEY: c3be326d-6592-4151-abad-7db160e6cfb8")
    @GET("cryptocurrency/listings/latest")
    suspend fun getCurrencies(
        @Query("limit") limit: Int,
        @Query("start") start: Int,
        @Query("convert") convert: String = "USD"
    ): ListingCurrencyResponse
}