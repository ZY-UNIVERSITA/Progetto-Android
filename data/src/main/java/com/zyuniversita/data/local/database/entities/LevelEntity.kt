package com.zyuniversita.data.local.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

/**
 * Represents a proficiency level in a given language.
 *
 * This entity is part of the Room database and corresponds to the `level` table.
 * Each entry is uniquely identified by the combination of a proficiency `code` 
 * (e.g., "HSK1", "N1", "TOPIK1") and a `languageCode` (e.g., "ch", "jp", "kr").
 *
 * ## Database Configuration:
 * - **Primary Keys:** Composite key consisting of `code` and `languageCode`, 
 *   allowing multiple languages to use the same proficiency code.
 * - **Foreign Key:** References [LanguageEntity] via the `languageCode` field.
 *   - `parentColumns`: ["code"]
 *   - `childColumns`: ["languageCode"]
 *   - `onDelete`: CASCADE — deletes related levels if the corresponding language is removed.
 * - **Index:** An index is created on `languageCode` to improve query performance
 *   when filtering by language.
 *
 * @property code The unique identifier of the proficiency level (e.g., "HSK1", "N1").
 * @property languageCode The code of the language to which this level belongs (e.g., "ch" for Chinese).
 */
@Entity(
    tableName = "level",
    primaryKeys = ["code", "languageCode"],
    foreignKeys = [ForeignKey(
        entity = LanguageEntity::class,
        parentColumns = ["code"],
        childColumns = ["languageCode"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("languageCode")]
)
data class LevelEntity(
    val code: String,
    val languageCode: String
)