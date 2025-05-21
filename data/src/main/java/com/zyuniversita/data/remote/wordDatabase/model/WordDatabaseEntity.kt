package com.zyuniversita.data.remote.wordDatabase.model

import com.squareup.moshi.JsonClass

/**
 * Represents a single word entry in the database.
 *
 * This data class is used by Moshi to (de)serialize JSON responses
 * from the word database endpoints.
 *
 * @property wordId          A unique identifier for the word.
 * @property levelCode       A code indicating the difficulty or category level.
 * @property languageCode    A two-letter code representing the language (e.g., "en", "es").
 * @property word             The word itself.
 * @property meaning          The definition or translation of the word.
 * @property transliteration  An optional transliteration of the word (if applicable).
 */
@JsonClass(generateAdapter = true)
data class WordDatabaseEntity(
    val wordId: Int,
    val levelCode: String,
    val languageCode: String,
    val word: String,
    val meaning: String,
    val transliteration: String? = null
)