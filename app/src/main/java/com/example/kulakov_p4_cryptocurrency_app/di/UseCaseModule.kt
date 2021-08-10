package com.example.kulakov_p4_cryptocurrency_app.di

import com.example.domain.repositories.local.IPreferncesStorage
import com.example.domain.repositories.remote.ICoinMarketCapRespository
import com.example.domain.repositories.remote.INewsApiRepository
import com.example.domain.use_cases.*
import com.example.domain.use_cases.preferences.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun providesGetSignInStatusUseCase(repository: IPreferncesStorage)
            = GetSignInStatusUseCase(repository)

    @Provides
    fun providesSaveSignInStatusUseCase(repository: IPreferncesStorage)
            = SaveSignInStatusUseCase(repository)

    @Provides
    fun providesGetCurrenciesUseCase(repository: ICoinMarketCapRespository)
            = GetCurrenciesUseCase(repository)

    @Provides
    fun providesGetCurrencyInfoUseCase(repository: ICoinMarketCapRespository)
            = GetCurrencyInfoUseCase(repository)

    @Provides
    fun providesGetNewsUseCase(repository: INewsApiRepository)
            = GetNewsUseCase(repository)

    @Provides
    fun providesGetLatestCurrencyUseCase(repository: ICoinMarketCapRespository)
            = GetLatestCurrencyUseCase(repository)

    @Provides
    fun providesUpdateCurrencyByIdUseCase(repository: ICoinMarketCapRespository)
            = UpdateCurrencyByIdUseCase(repository)

    @Provides
    fun providesGetLanguageUseCase(repository: IPreferncesStorage)
            = GetLanguageUseCase(repository)

    @Provides
    fun providesSaveLanguageUseCase(repository: IPreferncesStorage)
            = SaveLanguageUseCase(repository)

    @Provides
    fun providesGetThemeUseCase(repository: IPreferncesStorage)
            = GetThemeUseCase(repository)

    @Provides
    fun providesSaveThemeUseCase(repository: IPreferncesStorage)
            = SaveThemeUseCase(repository)

    @Provides
    fun providesSearchCurrencyByQuery(repository: ICoinMarketCapRespository)
            = SearchCurrencyByQuery(repository)
}