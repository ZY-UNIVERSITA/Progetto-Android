package com.zyuniversita.domain.di

import com.zyuniversita.domain.usecase.userdata.DeleteAllUserDataEntriesUseCase
import com.zyuniversita.domain.usecase.userdata.DeleteAllUserDataEntriesUseCaseImpl
import com.zyuniversita.domain.usecase.userdata.DeleteAllWordsDataEntriesUseCase
import com.zyuniversita.domain.usecase.userdata.DeleteAllWordsDataEntriesUseCaseImpl
import com.zyuniversita.domain.usecase.userdata.FetchUserQuizPerformanceUseCase
import com.zyuniversita.domain.usecase.userdata.FetchUserQuizPerformanceUseCaseImpl
import com.zyuniversita.domain.usecase.userdata.FetchWordsUserDataUseCase
import com.zyuniversita.domain.usecase.userdata.FetchWordsUserDataUseCaseImpl
import com.zyuniversita.domain.usecase.userdata.InsertAllUserDataEntriesUseCase
import com.zyuniversita.domain.usecase.userdata.InsertAllUserDataEntriesUseCaseImpl
import com.zyuniversita.domain.usecase.userdata.InsertAllWordsDataEntriesUseCase
import com.zyuniversita.domain.usecase.userdata.InsertAllWordsDataEntriesUseCaseImpl
import com.zyuniversita.domain.usecase.userdata.StartFetchUserQuizPerformanceUseCase
import com.zyuniversita.domain.usecase.userdata.StartFetchUserQuizPerformanceUseCaseImpl
import com.zyuniversita.domain.usecase.userdata.StartFetchWordsUserDataUseCase
import com.zyuniversita.domain.usecase.userdata.StartFetchWordsUserDataUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserDataModule {

    @Binds
    @Singleton
    abstract fun bindStartFetchWordsUserDataUseCase(
        startFetchWordsUserDataUseCaseImpl: StartFetchWordsUserDataUseCaseImpl
    ): StartFetchWordsUserDataUseCase

    @Binds
    @Singleton
    abstract fun bindFetchWordsUserDataUseCase(
        fetchWordsUserDataUseCaseImpl: FetchWordsUserDataUseCaseImpl
    ): FetchWordsUserDataUseCase

    @Binds
    @Singleton
    abstract fun bindDeleteAllWordsDataEntriesUseCase(
        deleteAllWordsDataEntriesUseCaseImpl: DeleteAllWordsDataEntriesUseCaseImpl
    ): DeleteAllWordsDataEntriesUseCase

    @Binds
    @Singleton
    abstract fun bindDeleteAllUserDataEntriesUseCase(
       deleteAllUserDataEntriesUseCaseImpl: DeleteAllUserDataEntriesUseCaseImpl
    ): DeleteAllUserDataEntriesUseCase

    @Binds
    @Singleton
    abstract fun bindInsertAllUserDataEntriesUseCase(
        insertAllUserDataEntriesUseCaseImpl: InsertAllUserDataEntriesUseCaseImpl
    ): InsertAllUserDataEntriesUseCase

    @Binds
    @Singleton
    abstract fun bindInsertAllWordsDataEntriesUseCase(
        insertAllWordsDataEntriesUseCaseImpl: InsertAllWordsDataEntriesUseCaseImpl
    ): InsertAllWordsDataEntriesUseCase

    @Binds
    @Singleton
    abstract fun bindStartFetchUserQuizPerformanceUseCase(
        startFetchUserQuizPerformanceUseCaseImpl: StartFetchUserQuizPerformanceUseCaseImpl
    ): StartFetchUserQuizPerformanceUseCase

    @Binds
    @Singleton
    abstract fun bindFetchUserQuizPerformanceUseCase(
        fetchUserQuizPerformanceUseCaseImpl: FetchUserQuizPerformanceUseCaseImpl
    ): FetchUserQuizPerformanceUseCase
}