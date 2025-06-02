package com.zyuniversita.domain.di

import com.zyuniversita.domain.usecase.profile.RemoveAllPreferencesUseCase
import com.zyuniversita.domain.usecase.profile.RemoveAllPreferencesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileUseCaseModule {

    @Binds
    @Singleton
    abstract fun bindRemoveAllPreferencesUseCase(
        removeAllPreferencesUseCaseImpl: RemoveAllPreferencesUseCaseImpl,
    ): RemoveAllPreferencesUseCase
}