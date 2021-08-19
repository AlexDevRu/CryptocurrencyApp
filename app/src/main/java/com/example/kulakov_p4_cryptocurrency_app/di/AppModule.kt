package com.example.kulakov_p4_cryptocurrency_app.di

import android.app.Application
import androidx.room.Room
import com.example.data.api.CoinMarketCapService
import com.example.data.api.NewsApiService
import com.example.data.database.CurrencyDatabase
import com.example.data.repositories.remote.CoinMarketCapRepository
import com.example.data.repositories.remote.NewsApiRepository
import com.example.domain.repositories.local.IPreferncesStorage
import com.example.domain.repositories.remote.ICoinMarketCapRepository
import com.example.data.repositories.local.PreferencesStorage
import com.example.data.repositories.remote.FirebaseRepository
import com.example.domain.repositories.remote.IFirebaseRepository
import com.example.domain.repositories.remote.INewsApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    val DATABASE_NAME = "currencies"

    @Provides
    fun providesCurrencyDatabase(app: Application): CurrencyDatabase = Room.databaseBuilder(
        app,
        CurrencyDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    fun providesCoinMarketCapRepository(
        service: CoinMarketCapService,
        firebaseRepository: IFirebaseRepository,
        currencyDatabase: CurrencyDatabase): ICoinMarketCapRepository
        = CoinMarketCapRepository(service, firebaseRepository, currencyDatabase)

    @Provides
    fun providesNewsRepository(service: NewsApiService, currencyDatabase: CurrencyDatabase): INewsApiRepository
            = NewsApiRepository(service, currencyDatabase)

    @Provides
    fun providesPreferencesStorage(app: Application): IPreferncesStorage
        = PreferencesStorage(app.applicationContext)

    @Provides
    fun providesFirebaseRepository(): IFirebaseRepository
            = FirebaseRepository()
}