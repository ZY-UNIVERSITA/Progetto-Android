package com.zyuniversita.domain.di

import com.zyuniversita.domain.usecase.synchronization.DownloadUserDataUseCase
import com.zyuniversita.domain.usecase.synchronization.DownloadUserDataUseCaseImpl
import com.zyuniversita.domain.usecase.synchronization.UploadUserData
import com.zyuniversita.domain.usecase.synchronization.UploadUserDataImpl
import com.zyuniversita.domain.usecase.worker.RemoveSynchronizationWorkerUseCase
import com.zyuniversita.domain.usecase.worker.RemoveSynchronizationWorkerUseCaseImpl
import com.zyuniversita.domain.usecase.worker.StartSynchronizationWorkerUseCase
import com.zyuniversita.domain.usecase.worker.StartSynchronizationWorkerUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SynchronizationUseCaseModule {
    @Binds
    @Singleton
    abstract fun bindStartSynchronizationWorkerUseCase(
        startSynchronizationWorkerUseCaseImpl: StartSynchronizationWorkerUseCaseImpl
    ): StartSynchronizationWorkerUseCase

    @Binds
    @Singleton
    abstract fun bindRemoveSynchronizationWorkerUseCase(
        removeSynchronizationWorkerUseCaseImpl: RemoveSynchronizationWorkerUseCaseImpl
    ): RemoveSynchronizationWorkerUseCase

    @Binds
    @Singleton
    abstract fun bindUploadUserData(
        uploadUserDataImpl: UploadUserDataImpl
    ): UploadUserData

    @Binds
    @Singleton
    abstract fun binDownloadUserDataUseCase(
        downloadUserDataUseCaseImpl: DownloadUserDataUseCaseImpl
    ): DownloadUserDataUseCase
}