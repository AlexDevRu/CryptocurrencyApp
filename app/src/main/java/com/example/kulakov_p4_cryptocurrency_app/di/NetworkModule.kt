package com.example.kulakov_p4_cryptocurrency_app.di

import com.example.data.api.ApiConstants.BASE_URL
import com.example.data.api.CoinMarketCapService
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
    fun providesRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun providesService(retrofit: Retrofit) = retrofit.create(CoinMarketCapService::class.java)
}