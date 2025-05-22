package com.zyuniversita.domain.model

/**
 * Represents the user's learning progress for a specific [Word].
 *
 * This model combines the core vocabulary data with user-specific metrics such as
 * selection status, quiz performance, and last interaction timestamp. It is typically
 * used to personalize learning experiences, track progress, or generate adaptive quizzes.
 *
 * @property word The vocabulary item being tracked.
 * @property selected Indicates whether the word has been manually selected by the user,
 *                    for inclusion in custom sessions or quizzes.
 * @property correct The number of times the user has answered this word correctly.
 * @property wrong The number of times the user has answered this word incorrectly.
 * @property lastSeen The timestamp (in milliseconds) of the last time this word was shown to the user,
 *                    or `null` if the word has never been seen.
 */
data class WordProgress(
    val word: Word,
    val selected: Boolean = false,
    val correct: Int = 0,
    val wrong: Int = 0,
    val lastSeen: Long? = null
)
