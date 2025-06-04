package com.zyuniversita.data.local.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

/**
 * Represents a user's quiz performance for a specific language.
 *
 * This entity maps to the `user_quiz_performance` table in the Room database.
 * Each entry uniquely identifies the performance of a specific user in a particular language.
 * It tracks the number of quizzes completed and the number of correct and wrong answers.
 *
 * ## Database Configuration:
 * - **Primary Keys:** Composite key of `userId` and `languageCode` ensures that each user
 *   has only one performance record per language.
 *
 * - **Foreign Keys:**
 *   - References [UserInfoEntity] using `userId`.
 *     - Deletes performance entries if the user is deleted (`onDelete = CASCADE`).
 *   - References [LanguageEntity] using `languageCode`.
 *     - Deletes performance entries if the language is deleted (`onDelete = CASCADE`).
 *
 * - **Indices:**
 *   - `userId` and `languageCode` are indexed to improve performance on queries
 *     involving user or language filtering.
 *
 * @property userId The unique identifier of the user. References [UserInfoEntity].
 * @property languageCode The code of the language for which the performance is tracked.
 *                        References [LanguageEntity].
 * @property completedQuiz The total number of completed quizzes for this user-language pair. Defaults to 1.
 * @property correctAnswer The total number of correct answers given by the user. Defaults to 0.
 * @property wrongAnswer The total number of wrong answers given by the user. Defaults to 0.
 */
@Entity(

    tableName = "user_quiz_performance",
    primaryKeys = ["userId", "languageCode"],
    foreignKeys = [
        ForeignKey(
            entity = UserInfoEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LanguageEntity::class,
            parentColumns = ["code"],
            childColumns = ["languageCode"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["userId"]),
        Index(value = ["languageCode"])
    ]
)
data class UserQuizPerformanceEntity(
    val userId: Int,
    val languageCode: String,
    val completedQuiz: Int = 1,
    val correctAnswer: Int = 0,
    val wrongAnswer: Int = 0
)
