package com.example.kulakov_p4_cryptocurrency_app.di

import com.example.domain.repositories.local.IPreferncesStorage
import com.example.domain.repositories.remote.ICoinMarketCapRespository
import com.example.domain.use_cases.GetCurrenciesUseCase
import com.example.domain.use_cases.preferences.GetSignInStatusUseCase
import com.example.domain.use_cases.preferences.SaveSignInStatusUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun providesGetCurrenciesUseCase(repository: ICoinMarketCapRespository)
            = GetCurrenciesUseCase(repository)

    @Provides
    fun providesGetSignInStatusUseCase(repository: IPreferncesStorage)
            = GetSignInStatusUseCase(repository)

    @Provides
    fun providesSaveSignInStatusUseCase(repository: IPreferncesStorage)
            = SaveSignInStatusUseCase(repository)
}