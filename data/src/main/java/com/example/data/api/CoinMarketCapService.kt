package com.example.data.api

import com.example.data.api.responses.CurrencyInfoResponse
import com.example.data.api.responses.ListingCurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CoinMarketCapService {
    //c3be326d-6592-4151-abad-7db160e6cfb8
    //2a2112c8-7c8c-4405-bd25-8362e7ab14bc
    @Headers("X-CMC_PRO_API_KEY: c3be326d-6592-4151-abad-7db160e6cfb8")
    @GET("cryptocurrency/listings/latest")
    suspend fun getCurrencies(
        @Query("limit") limit: Int,
        @Query("start") start: Int,
        @Query("cryptocurrency_type") type: String = "all",
        @Query("tag") tag: String = "all",
        @Query("price_min") priceMin: Double,
        @Query("price_max") priceMax: Double,
        @Query("market_cap_min") marketCapMin: Double,
        @Query("market_cap_max") marketCapMax: Double,
        @Query("sort") sort: String,
        @Query("sort_dir") sortDir: String
    ): ListingCurrencyResponse


    @Headers("X-CMC_PRO_API_KEY: c3be326d-6592-4151-abad-7db160e6cfb8")
    @GET("cryptocurrency/info")
    suspend fun getCurrencyInfo(
        @Query("id") ids: String
    ): CurrencyInfoResponse

    @Headers("X-CMC_PRO_API_KEY: c3be326d-6592-4151-abad-7db160e6cfb8")
    @GET("cryptocurrency/listings/latest")
    suspend fun getLatestCurrency(
        @Query("limit") limit: Int = 1,
        @Query("start") start: Int = 1
    ): ListingCurrencyResponse
}