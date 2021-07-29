package com.example.kulakov_p4_cryptocurrency_app.di

import android.app.Application
import com.example.data.api.CoinMarketCapService
import com.example.data.repositories.CoinMarketCapRepository
import com.example.domain.repositories.local.IPreferncesStorage
import com.example.domain.repositories.remote.ICoinMarketCapRespository
import com.example.data.repositories.local.PreferencesStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun providesCoinMarketCapRespository(service: CoinMarketCapService): ICoinMarketCapRespository
        = CoinMarketCapRepository(service)

    @Provides
    fun providesPreferencesStorage(app: Application): IPreferncesStorage
        = PreferencesStorage(app.applicationContext)
}