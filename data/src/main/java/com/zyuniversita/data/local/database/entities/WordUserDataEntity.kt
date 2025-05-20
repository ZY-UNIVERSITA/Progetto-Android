package com.zyuniversita.data.local.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey

// TODO: 
// Add multi user
// Change name to word performance


/**
 * Stores user-specific performance and interaction data for a particular word.
 *
 * This entity corresponds to the `word_user_data` table in the Room database.
 * It tracks how a user interacts with a given word, such as whether they selected it,
 * how many times they answered correctly or incorrectly, and the last time the word was seen.
 *
 * ## Database Configuration:
 * - **Primary Key:** `wordId` is used as the primary key, assuming that each word has only one
 *   user-related data entry (per user, if multi-user is supported, it may need to be extended).
 *
 * - **Foreign Key:**
 *   - References [WordEntity] using `wordId`.
 *   - When a word is deleted from the database, the corresponding user data is also removed (`onDelete = CASCADE`).
 *
 * @property wordId The unique identifier of the word. References [WordEntity].
 * @property selected A flag indicating whether the user has selected or marked this word (e.g., as favorite or for review). Defaults to `false`.
 * @property correctCount Number of times the user answered correctly when encountering this word. Defaults to `0`.
 * @property wrongCount Number of times the user answered incorrectly when encountering this word. Defaults to `0`.
 * @property lastSeen Timestamp (in milliseconds) indicating the last time the word was shown to the user. Can be `null` if never seen.
 */
@Entity(
    tableName = "word_user_data",
    primaryKeys = ["wordId"],
    foreignKeys = [ForeignKey(
        entity = WordEntity::class,
        parentColumns = ["wordId"],
        childColumns = ["wordId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class WordUserDataEntity(
    val wordId: Int,
    val selected: Boolean = false,
    val correctCount: Int = 0,
    val wrongCount: Int = 0,
    val lastSeen: Long? = null
)

