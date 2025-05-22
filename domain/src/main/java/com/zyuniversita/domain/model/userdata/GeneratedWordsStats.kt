package com.zyuniversita.domain.model.userdata

/**
 * Represents generated statistics for a word based on user interaction.
 *
 * @property correct The number of times the word was answered correctly.
 * @property incorrect The number of times the word was answered incorrectly.
 */
data class GeneratedWordsStats(
    val correct: Int = 0,
    val incorrect: Int = 0
)
