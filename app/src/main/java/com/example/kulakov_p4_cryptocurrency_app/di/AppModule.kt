package com.example.kulakov_p4_cryptocurrency_app.di

import android.app.Application
import com.example.data.api.CoinMarketCapService
import com.example.data.api.NewsApiService
import com.example.data.repositories.CoinMarketCapRepository
import com.example.data.repositories.NewsApiRepository
import com.example.domain.repositories.local.IPreferncesStorage
import com.example.domain.repositories.remote.ICoinMarketCapRespository
import com.example.data.repositories.local.PreferencesStorage
import com.example.domain.repositories.remote.INewsApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun providesCoinMarketCapRepository(service: CoinMarketCapService): ICoinMarketCapRespository
        = CoinMarketCapRepository(service)

    @Provides
    fun providesNewsRepository(service: NewsApiService): INewsApiRepository
            = NewsApiRepository(service)

    @Provides
    fun providesPreferencesStorage(app: Application): IPreferncesStorage
        = PreferencesStorage(app.applicationContext)
}