package com.example.kulakov_p4_cryptocurrency_app.di

import com.example.data.api.ApiConstants
import com.example.data.api.CoinMarketCapService
import com.example.data.api.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun providesCoinMarketCapService() =
        Retrofit.Builder()
            .baseUrl(ApiConstants.COIN_MARKET_CAP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinMarketCapService::class.java)

    @Provides
    fun providesNewsService() =
        Retrofit.Builder()
            .baseUrl(ApiConstants.NEWS_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
}