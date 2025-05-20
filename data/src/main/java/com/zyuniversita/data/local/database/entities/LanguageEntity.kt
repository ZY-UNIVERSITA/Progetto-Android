package com.zyuniversita.data.local.database.entities

import androidx.room.Entity

/**
 * Represents a language supported by the application, stored in the "language" table.
 *
 * Each LanguageEntity:
 *  • Is uniquely identified by [code].
 *  • Holds the human‐readable [name] of the language.
 *
 * Table configuration:
 *  • tableName = "language"  
 *  • primaryKeys = ["code"]
 *
 * @param code Code of the language (e.g., "ch", "jp", "kr").
 * @param name Full name of the language (e.g., "Chinese", "Japanese", "Korean").
 */
@Entity(
    tableName = "language",
    primaryKeys = ["code"]
)
data class LanguageEntity(
    val code: String,
    val name: String
)
