package com.zyuniversita.data.di

import com.zyuniversita.data.local.assets.ReadWordsFileDataSource
import com.zyuniversita.data.local.assets.ReadWordsFileDataSourceImpl
import com.zyuniversita.data.remote.authentication.RemoteAuthenticationDataSource
import com.zyuniversita.data.remote.authentication.RemoteAuthenticationDataSourceImpl
import com.zyuniversita.data.remote.imagerecognition.RemoteImageRecognitionDataSource
import com.zyuniversita.data.remote.imagerecognition.RemoteImageRecognitionDataSourceImpl
import com.zyuniversita.data.remote.synchronization.RemoteSynchronizationDataSource
import com.zyuniversita.data.remote.synchronization.RemoteSynchronizationDataSourceImpl
import com.zyuniversita.data.remote.wordDatabase.RemoteWordDatabaseDataSource
import com.zyuniversita.data.remote.wordDatabase.RemoteWordDatabaseDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides bindings for data sources used in the application.
 *
 * This module is installed in the [SingletonComponent], meaning the provided dependencies
 * will have a singleton lifecycle and be available throughout the entire app.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceProviderModule {

    /**
     * Binds the implementation [RemoteWordDatabaseDataSourceImpl] to the interface [RemoteWordDatabaseDataSource].
     *
     * This enables Hilt to inject [RemoteWordDatabaseDataSource] wherever it's required,
     * providing a singleton instance of [RemoteWordDatabaseDataSourceImpl].
     *
     * @param remoteWordDatabaseDataSourceImpl The implementation to bind.
     * @return The bound [RemoteWordDatabaseDataSource] instance.
     */
    @Binds
    @Singleton
    abstract fun bindRemoteWordDatabaseDataSource(
        remoteWordDatabaseDataSourceImpl: RemoteWordDatabaseDataSourceImpl
    ): RemoteWordDatabaseDataSource

    /**
     * Binds the implementation [RemoteImageRecognitionDataSourceImpl] to the interface [RemoteImageRecognitionDataSource].
     *
     * This allows dependency injection of [RemoteImageRecognitionDataSource] throughout the app,
     * mapping it to the provided singleton implementation.
     *
     * @param remoteImageRecognitionDataSourceImpl The implementation to bind.
     * @return The bound [RemoteImageRecognitionDataSource] instance.
     */
    @Binds
    @Singleton
    abstract fun bindRemoteImageRecognitionDataSource(
        remoteImageRecognitionDataSourceImpl: RemoteImageRecognitionDataSourceImpl
    ): RemoteImageRecognitionDataSource


    @Binds
    @Singleton
    abstract fun bindRemoteAuthenticationDataSource(
        remoteAuthenticationDataSourceImpl: RemoteAuthenticationDataSourceImpl
    ): RemoteAuthenticationDataSource

    @Binds
    @Singleton
    abstract fun bindRemoteSynchronizationDataSource(
        remoteSynchronizationDataSourceImpl: RemoteSynchronizationDataSourceImpl
    ): RemoteSynchronizationDataSource

    @Binds
    @Singleton
    abstract fun bindReadWordsFileDataSource(
        readWordsFileDataSourceImpl: ReadWordsFileDataSourceImpl
    ): ReadWordsFileDataSource
}
