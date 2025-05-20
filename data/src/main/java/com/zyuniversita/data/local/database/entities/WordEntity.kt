package com.zyuniversita.data.local.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

/**
 * Represents a vocabulary word stored in the “word” table.
 *
 * Each WordEntity:
 *  - Is uniquely identified by [wordId].
 *  - Belongs to a specific level ([levelCode]) in a particular language ([languageCode]).
 *  - Holds the actual word text, its meaning, and an optional transliteration.
 *
 * Table configuration:
 *  • tableName = "word"  
 *  • primaryKeys = ["wordId"]  
 *  • foreignKeys = [ForeignKey to [LevelEntity] on (code, languageCode)
 *      – child columns: ["levelCode", "languageCode"]
 *      – onDelete = CASCADE
 *    ]  
 *  • indices = [Index on ["levelCode", "languageCode"] for faster lookups]
 *
 * @param wordId         Unique identifier for this word.
 * @param levelCode      Code of the level this word belongs to.
 * @param languageCode   Code of the language for this word.
 * @param word           The word itself.
 * @param meaning        The meaning or translation of the word.
 * @param transliteration Optional phonetic transliteration (if any).
 */
@Entity(
    tableName = "word",
    primaryKeys = ["wordId"],
    foreignKeys = [
        ForeignKey(
            entity = LevelEntity::class,
            parentColumns = ["code", "languageCode"],
            childColumns = ["levelCode", "languageCode"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["levelCode", "languageCode"])
    ]
)
data class WordEntity(
    val wordId: Int,
    val levelCode: String,
    val languageCode: String,
    val word: String,
    val meaning: String,
    val transliteration: String? = null
)
