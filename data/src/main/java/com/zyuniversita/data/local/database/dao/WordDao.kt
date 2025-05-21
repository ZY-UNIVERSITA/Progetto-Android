package com.zyuniversita.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zyuniversita.data.local.database.entities.WordEntity
import com.zyuniversita.data.local.database.relations.WordWithUserData

/**
 * Data Access Object (DAO) for managing word-related database operations.
 *
 * This interface provides methods to:
 * - Insert a list of words.
 * - Retrieve words filtered by language and/or level.
 * - Retrieve words joined with the associated user data.
 * - Fetch words that have been marked as selected for quiz purposes.
 */
@Dao
interface WordDao {

    /**
     * Inserts a list of [WordEntity] objects into the database.
     *
     * If a conflict occurs (e.g., a duplicate primary key), the existing entry will be replaced.
     *
     * @param list A list of [WordEntity] objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(list: List<WordEntity>)

    /**
     * Retrieves the words for a specific language and level.
     *
     * The words are ordered by their [WordEntity.wordId].
     *
     * @param lang The language code used to filter the words.
     * @param level The level code used to further filter the words.
     * @return A list of [WordEntity] objects matching the specified language and level.
     */
    @Query(
        """
            SELECT * 
            FROM word
            WHERE languageCode = :lang AND levelCode = :level
            ORDER BY wordId
        """
    )
    suspend fun wordsByLevelAndLanguage(lang: String, level: String): List<WordEntity>

    /**
     * Retrieves all words for a specific language.
     *
     * The words are ordered by their [WordEntity.wordId].
     *
     * @param lang The language code used to filter the words.
     * @return A list of [WordEntity] objects matching the specified language.
     */
    @Query(
        """
            SELECT * 
            FROM word
            WHERE languageCode = :lang
            ORDER BY wordId
        """
    )
    suspend fun fetchWordsByLanguage(lang: String): List<WordEntity>

    /**
     * Retrieves all words for a specific language, joining them with any associated user data.
     *
     * If a user has never interacted with a specific word, the corresponding user data will be null.
     * The words are ordered by their [WordEntity.wordId].
     *
     * @param lang The language code used to filter the words.
     * @return A list of [WordWithUserData] objects containing the word and its associated user data.
     */
    @Transaction
    @Query(
        """
            SELECT * 
            FROM word
            WHERE languageCode = :lang
            ORDER BY wordId
        """
    )
    suspend fun wordsWithUserData(lang: String): List<WordWithUserData>

    /**
     * Retrieves all words for a specific language and level, joining them with any associated user data.
     *
     * If a user has never interacted with a specific word, the corresponding user data will be null.
     * The words are ordered by their [WordEntity.wordId].
     *
     * @param lang The language code used to filter the words.
     * @param level The level code used to further filter the words.
     * @return A list of [WordWithUserData] objects containing the word and its associated user data.
     */
    @Transaction
    @Query(
        """
            SELECT * 
            FROM word
            WHERE languageCode = :lang AND levelCode = :level
            ORDER BY wordId
        """
    )
    suspend fun wordsWithUserData(lang: String, level: String): List<WordWithUserData>

    /**
     * Retrieves a single word by its identifier, joining it with any associated user data.
     *
     * If there is no corresponding user data for the word, the user data field will be null.
     *
     * @param wordId The unique identifier of the word.
     * @return A [WordWithUserData] object containing the word and its associated user data.
     */
    @Transaction
    @Query(
        """
            SELECT *
            FROM word
            WHERE wordId = :wordId
        """
    )
    suspend fun fetchSingleWordWithUserData(wordId: Int): WordWithUserData

    /**
     * Retrieves words for a specific language that have been marked as selected for the quiz.
     *
     * This method performs a join between the words table and the user data table. Only the words that are
     * marked as selected (i.e., where [WordUserDataEntity.selected] is 1) are returned.
     * The words are ordered by their [WordEntity.wordId].
     *
     * @param lang The language code used to filter the words.
     * @return A list of [WordWithUserData] objects where each word has been selected for the quiz.
     */
    @Transaction
    @Query(
        """
            SELECT * 
            FROM word
            WHERE languageCode = :lang
              AND wordId IN (
                    SELECT wordId
                    FROM word_user_data
                    WHERE selected = 1
              )
            ORDER BY wordId
        """
    )
    suspend fun selectedForQuiz(lang: String): List<WordWithUserData>
}