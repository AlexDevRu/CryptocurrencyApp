package com.example.kulakov_p4_cryptocurrency_app.di

import com.example.domain.repositories.local.IPreferncesStorage
import com.example.domain.repositories.remote.ICoinMarketCapRepository
import com.example.domain.repositories.remote.IFirebaseRepository
import com.example.domain.repositories.remote.INewsApiRepository
import com.example.domain.use_cases.*
import com.example.domain.use_cases.firebase.DeleteFavoriteCurrencyUseCase
import com.example.domain.use_cases.firebase.SaveFavoriteCurrencyUseCase
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
    fun providesGetCurrenciesUseCase(repository: ICoinMarketCapRepository)
            = GetCurrenciesUseCase(repository)

    @Provides
    fun providesGetCurrencyInfoUseCase(repository: ICoinMarketCapRepository)
            = GetCurrencyInfoUseCase(repository)

    @Provides
    fun providesGetNewsUseCase(repository: INewsApiRepository)
            = GetNewsUseCase(repository)

    @Provides
    fun providesGetLatestCurrencyUseCase(repository: ICoinMarketCapRepository)
            = GetLatestCurrencyUseCase(repository)

    @Provides
    fun providesGetUpdatedCurrencyUseCase(repository: ICoinMarketCapRepository)
            = GetUpdatedCurrencyUseCase(repository)

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
    fun providesSearchCurrencyByQuery(repository: ICoinMarketCapRepository)
            = SearchCurrencyByQueryUseCase(repository)

    @Provides
    fun providesGetFavoriteCurrenciesUseCase(
        coinMarketCapRepository: ICoinMarketCapRepository
    ) = GetFavoriteCurrenciesUseCase(coinMarketCapRepository)

    @Provides
    fun providesSaveFavoriteCurrencyUseCase(
        firebaseRepository: IFirebaseRepository,
        coinMarketCapRepository: ICoinMarketCapRepository
    ) = SaveFavoriteCurrencyUseCase(firebaseRepository, coinMarketCapRepository)

    @Provides
    fun providesDeleteFavoriteCurrencyUseCase(
        firebaseRepository: IFirebaseRepository,
        coinMarketCapRepository: ICoinMarketCapRepository
    ) = DeleteFavoriteCurrencyUseCase(firebaseRepository, coinMarketCapRepository)
}