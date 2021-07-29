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
        @Query("cryptocurrency_type") type: String,
        @Query("tag") tag: String,
        @Query("price_min") priceMin: Float,
        @Query("price_max") priceMax: Float,
        @Query("market_cap_min") marketCapMin: Float,
        @Query("market_cap_max") marketCapMax: Float,
        @Query("sort") sort: String,
        @Query("sort_dir") sortDir: String,
        @Query("convert") convert: String = "USD"
    ): ListingCurrencyResponse
}