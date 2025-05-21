package com.zyuniversita.data.remote.wordDatabase

import com.zyuniversita.data.remote.wordDatabase.api.WordDatabaseApi
import com.zyuniversita.data.remote.wordDatabase.model.WordDatabaseEntity
import javax.inject.Inject

/**
 * Data source interface for accessing the remote word database.
 * 
 * Provides methods to fetch the current database version and retrieve the word database
 * data for a specified version from a remote API.
 */
interface RemoteWordDatabaseDataSource {

    /**
     * Fetches the current version number of the remote word database.
     *
     * @return An [Int] representing the latest version of the word database.
     */
    suspend fun fetchVersion(): Int

    /**
     * Fetches the word database data for the provided version.
     *
     * @param version The version of the word database to fetch.
     * @return A [List] of [WordDatabaseEntity] representing the word data.
     */
    suspend fun fetchDatabase(version: Int): List<WordDatabaseEntity>
}

/**
 * Implementation of [RemoteWordDatabaseDataSource] that uses [WordDatabaseApi]
 * to interact with the remote server.
 *
 * @param wordDatabaseApi The API used to perform remote operations related to the word database.
 */
class RemoteWordDatabaseDataSourceImpl @Inject constructor(
    private val wordDatabaseApi: WordDatabaseApi
) : RemoteWordDatabaseDataSource {

    /**
     * {@inheritDoc}
     */
    override suspend fun fetchVersion(): Int {
        return wordDatabaseApi.getWordDatabaseVersion().version
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun fetchDatabase(version: Int): List<WordDatabaseEntity> {
        return wordDatabaseApi.getWordDatabase(version)
    }
}
