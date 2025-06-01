package com.zyuniversita.domain.di

import com.zyuniversita.domain.usecase.authentication.LoginUseCase
import com.zyuniversita.domain.usecase.authentication.LoginUseCaseImpl
import com.zyuniversita.domain.usecase.authentication.RegisterUseCase
import com.zyuniversita.domain.usecase.authentication.RegisterUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthenticationUseCaseModule {

    @Binds
    @Singleton
    abstract fun bindRegisterUseCase(
        registerUseCaseImpl: RegisterUseCaseImpl
    ): RegisterUseCase

    @Binds
    @Singleton
    abstract fun bindLoginUseCase(
        loginUseCaseImpl: LoginUseCaseImpl
    ): LoginUseCase

}