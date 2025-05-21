package com.zyuniversita.data.remote.wordDatabase.api

import com.zyuniversita.data.remote.wordDatabase.model.WordDatabaseEntity
import com.zyuniversita.data.remote.wordDatabase.model.WordDatabaseVersion
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit API for interacting with the word database.
 *
 * Provides endpoints to:
 *  1. Retrieve the current database version.
 *  2. Fetch the database contents for a specific version.
 */
interface WordDatabaseApi {

    /**
     * Retrieves the current word database version.
     *
     * Sends a GET request to `/word-database/version`.
     *
     * @return [WordDatabaseVersion] containing details about the latest database version.
     */
    @GET("/word-database/version")
    suspend fun getWordDatabaseVersion(): WordDatabaseVersion

    /**
     * Retrieves the word database entities for a given version.
     *
     * Sends a GET request to `/word-database/json/{version}`.
     *
     * @param version The version number of the word database to fetch.
     * @return A [List] of [WordDatabaseEntity] representing all entries in that version.
     */
    @GET("/word-database/json/{version}")
    suspend fun getWordDatabase(
        @Path("version") version: Int
    ): List<WordDatabaseEntity>
}