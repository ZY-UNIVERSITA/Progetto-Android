package com.zyuniversita.domain.model.words

/**
 * Represents a word entity in the application's domain model.
 *
 * This data class is typically used to model a vocabulary item in a specific language and level,
 * including its meaning and optional transliteration (for languages using non-Latin scripts).
 *
 * @property wordId A unique identifier for the word.
 * @property levelCode The level or difficulty code associated with this word (e.g., "A1", "B2").
 * @property languageCode The language code this word belongs to (e.g., "en", "es", "ja").
 * @property word The actual word or vocabulary term.
 * @property meaning The definition or translation of the word, intended for learning purposes.
 * @property transliteration An optional phonetic representation of the word,
 *                           useful for languages with non-Latin scripts (e.g., Japanese, Arabic).
 */
data class Word(
    val wordId: Int,
    val levelCode: String,
    val languageCode: String,
    val word: String,
    val meaning: String,
    val transliteration: String?
)
