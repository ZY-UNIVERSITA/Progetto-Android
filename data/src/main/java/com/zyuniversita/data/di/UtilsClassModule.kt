package com.zyuniversita.data.di

import com.zyuniversita.data.utils.mapper.DataMapper
import com.zyuniversita.data.utils.mapper.DataMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * A Hilt module that provides and binds utility classes.
 *
 * This module installs its bindings into the SingletonComponent, 
 * ensuring that the provided instances live as long as the application.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class UtilsClassModule {

    /**
     * Binds the DataMapperImpl implementation to the DataMapper interface.
     *
     * By using @Binds, Hilt knows to use the provided [DataMapperImpl]
     * whenever a [DataMapper] is requested.
     *
     * @param dataMapperImpl the concrete implementation of [DataMapper]
     * @return the [DataMapper] interface bound to [DataMapperImpl]
     */
    @Binds
    @Singleton
    abstract fun bindDataMapper(
        dataMapperImpl: DataMapperImpl
    ): DataMapper
}
