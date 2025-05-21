package com.zyuniversita.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zyuniversita.data.local.database.entities.UserQuizPerformanceEntity
import com.zyuniversita.data.local.database.relations.UserQuizPerformanceWithLanguage

/**
 * Data Access Object (DAO) for managing user quiz performance data.
 *
 * This interface provides methods to:
 * - Insert multiple or single user quiz performance records.
 * - Update existing quiz performance data by incrementing the completed quiz,
 *   correct answer, and wrong answer counts.
 * - Retrieve quiz performance data either by combining user and language information
 *   or filtered by a specific user and language.
 */
@Dao
interface UserQuizPerformanceDao {

    /**
     * Inserts a list of [UserQuizPerformanceEntity] objects into the database.
     *
     * If a conflict occurs (e.g., a record with the same primary key already exists),
     * the existing entry will be replaced.
     *
     * @param list A list of [UserQuizPerformanceEntity] objects to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<UserQuizPerformanceEntity>): Unit

    /**
     * Inserts a single [UserQuizPerformanceEntity] object into the database.
     *
     * If a conflict occurs, the existing entry will be replaced.
     *
     * @param list The [UserQuizPerformanceEntity] object to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(list: UserQuizPerformanceEntity): Unit

    /**
     * Updates the quiz performance data for a specific user and language by incrementing:
     * - The number of completed quizzes.
     * - The number of correct answers by [correctAnswer].
     * - The number of wrong answers by [wrongAnswer].
     *
     * @param userId The unique identifier of the user whose data is being updated.
     * @param languageCode The language code used to filter the quiz performance record.
     * @param correctAnswer The count value to add to the current number of correct answers.
     * @param wrongAnswer The count value to add to the current number of wrong answers.
     * @return The number of rows affected by this update.
     */
    @Query(
        """
            UPDATE user_quiz_performance
            SET 
                completedQuiz = completedQuiz + 1,
                correctAnswer = correctAnswer + :correctAnswer,
                wrongAnswer = wrongAnswer + :wrongAnswer
            WHERE userId = :userId AND languageCode = :languageCode
        """
    )
    suspend fun updateUserData(userId: Long, languageCode: String, correctAnswer: Int, wrongAnswer: Int): Int

    /**
     * Retrieves the quiz performance data for a specific user and language.
     *
     * @param userId The unique identifier of the user.
     * @param languageCode The language code to filter the quiz performance record.
     * @return A [UserQuizPerformanceEntity] object representing the quiz performance for the specified user and language.
     */
    @Query(
        """
            SELECT * 
            FROM user_quiz_performance
            WHERE userId = :userId AND languageCode = :languageCode
        """
    )
    suspend fun getUserDataByUserIdAndLanguage(userId: Long, languageCode: String): UserQuizPerformanceEntity

    /**
     * Retrieves all quiz performance data for a specific user, including the associated language information.
     *
     * The query returns a list of [UserQuizPerformanceWithLanguage] objects, which combine
     * the quiz performance data from [UserQuizPerformanceEntity] with the corresponding language details.
     *
     * @param userId The unique identifier of the user.
     * @return A list of [UserQuizPerformanceWithLanguage] objects containing the quiz performance and language data.
     */
    @Transaction
    @Query(
        """
            SELECT * 
            FROM user_quiz_performance
            WHERE userId = :userId
        """
    )
    suspend fun getUserDataByUserId(userId: Long): List<UserQuizPerformanceWithLanguage>
}