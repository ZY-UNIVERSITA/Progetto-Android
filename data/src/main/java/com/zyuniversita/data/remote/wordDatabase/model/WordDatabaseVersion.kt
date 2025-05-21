package com.zyuniversita.data.remote.wordDatabase.model

import com.squareup.moshi.JsonClass

/**
 * Represents the version information of the word database.
 *
 * This data class is used by Moshi to (de)serialize the JSON response
 * from the `/word-database/version` endpoint.
 *
 * @property version The current version number of the word database.
 */
@JsonClass(generateAdapter = true)
data class WordDatabaseVersion(
    val version: Int
)