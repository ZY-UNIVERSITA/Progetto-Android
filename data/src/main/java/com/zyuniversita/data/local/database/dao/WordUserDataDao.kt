package com.zyuniversita.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zyuniversita.data.local.database.entities.WordUserDataEntity

/**
 * Data Access Object (DAO) for managing user-specific word data.
 *
 * This interface provides methods to insert new word data records, update existing ones, 
 * and query records based on the word identifier.
 */
@Dao
interface WordUserDataDao {

    /**
     * Inserts or replaces a [WordUserDataEntity] record in the database.
     *
     * If a conflict occurs (e.g., if a record with the same primary key exists), 
     * the existing record will be replaced with the provided [data].
     *
     * @param data The [WordUserDataEntity] object to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: WordUserDataEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<WordUserDataEntity>)

    /**
     * Inserts a new entry into the word_user_data table with the specified values.
     *
     * This query inserts a new record with the provided [wordId], [newCount], [wrongCount],
     * and [lastSeen] timestamp. If the entry already exists with the same primary key,
     * it will be replaced, as defined by the table constraints.
     *
     * @param wordId The unique identifier of the word.
     * @param newCount The number of correct answers to register.
     * @param wrongCount The number of wrong answers to register.
     * @param lastSeen The timestamp indicating when the word was last seen (default is the current time).
     */
    @Query(
        """
            INSERT INTO word_user_data (wordId, correctCount, wrongCount, lastSeen)
            VALUES (:wordId, :newCount, :wrongCount, :lastSeen)
        """
    )
    suspend fun insertUserData(
        wordId: Int,
        newCount: Int,
        wrongCount: Int,
        lastSeen: Long = System.currentTimeMillis()
    )

    /**
     * Updates an existing entry in the word_user_data table for the given [wordId].
     *
     * The update increments the [correctCount] by [newCount] and the [wrongCount] by [wrongCount],
     * and it sets the [lastSeen] timestamp to the provided value.
     *
     * @param wordId The unique identifier of the word.
     * @param newCount The number to add to the current correct count.
     * @param wrongCount The number to add to the current wrong count.
     * @param lastSeen The new timestamp to be set for the last seen time (default is the current time).
     * @return The number of rows affected by the update.
     */
    @Query(
        """
            UPDATE word_user_data
            SET
                correctCount = word_user_data.correctCount + :newCount,
                wrongCount = word_user_data.wrongCount + :wrongCount,
                lastSeen = :lastSeen
            WHERE wordId = :wordId
        """
    )
    suspend fun updateUserData(
        wordId: Int,
        newCount: Int,
        wrongCount: Int,
        lastSeen: Long = System.currentTimeMillis()
    ): Int

    @Query(
        """
            SELECT *
            FROM word_user_data
        """
    )
    suspend fun getAllUserData(): List<WordUserDataEntity>

    /**
     * Retrieves the word user data for a specific [wordId].
     *
     * @param wordId The unique identifier of the word.
     * @return A [WordUserDataEntity] representing the data for the word, or null if no record is found.
     */
    @Query(
        """
            SELECT *
            FROM word_user_data
            WHERE wordId = :wordId
        """
    )
    suspend fun getByWordID(wordId: Int): WordUserDataEntity?

    /**
     * Updates the selected state for a specific word.
     *
     * This query sets the [selected] flag to the provided [isSelected] value for the record with the given [wordId].
     * It only updates the record if the new value is different from the current one.
     *
     * @param wordId The unique identifier of the word.
     * @param isSelected The new selected state to be applied.
     * @return The number of rows affected by the update.
     */
    @Query(
        """
            UPDATE word_user_data
            SET selected = :isSelected
            WHERE wordId = :wordId
        """
    )
    suspend fun setSelected(wordId: Int, isSelected: Boolean): Int

    @Query(
        """
                DELETE
                FROM word_user_data
        """
    )
    suspend fun deleteAllEntries()
}