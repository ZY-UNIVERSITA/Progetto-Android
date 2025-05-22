package com.zyuniversita.domain.model.userdata

/**
 * Represents general quiz performance statistics for a user in a specific language for a specific word.
 *
 * @property languageCode The ISO code or app-specific identifier for the language (e.g., "en", "it").
 * @property languageName The human-readable name of the language (e.g., "English", "Italian").
 * @property completedQuiz The total number of quizzes the user has completed in this language.
 * @property correctAnswer The total number of correct answers given by the user in this language.
 * @property wrongAnswer The total number of incorrect answers given by the user in this language.
 */
data class GeneralUserStats(
    val languageCode: String,
    val languageName: String,
    val completedQuiz: Int,
    val correctAnswer: Int,
    val wrongAnswer: Int,
)
