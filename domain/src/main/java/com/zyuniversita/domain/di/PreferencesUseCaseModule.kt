package com.zyuniversita.domain.di

import com.zyuniversita.domain.usecase.preferences.FetchSynchronizationUseCase
import com.zyuniversita.domain.usecase.preferences.FetchSynchronizationUseCaseImpl
import com.zyuniversita.domain.usecase.preferences.SetSynchronizationUseCase
import com.zyuniversita.domain.usecase.preferences.SetSynchronizationUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesUseCaseModule {

    @Binds
    @Singleton
    abstract fun bindFetchSynchronization(
        fetchSynchronizationUseCaseImpl: FetchSynchronizationUseCaseImpl
    ): FetchSynchronizationUseCase

    @Binds
    @Singleton
    abstract fun binSetSynchronization(
        setSynchronizationUseCaseImpl: SetSynchronizationUseCaseImpl
    ): SetSynchronizationUseCase
}