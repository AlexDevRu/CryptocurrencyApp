package com.example.data.api

import com.example.data.api.responses.news.ListingNewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("search")
    suspend fun getNewsByQuery(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("max") pageSize: Int,
        @Query("token") token: String = "12dc538d3108bb6c3de859ab2ac9a637",
    ): ListingNewsResponse
}