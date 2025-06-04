package com.zyuniversita.domain.model.words

/**
 * Represents a multiple-choice quiz question based on a word and its related options.
 *
 * This model encapsulates the core structure of a question used in a quiz context,
 * with one correct solution among a set of possible answers. Each [WordProgress]
 * item contains both the word and the user's learning stats for that word.
 *
 * @property question The main question word that the user needs to answer about.
 * @property answers A list of possible answers (including the correct one),
 *                   represented as [WordProgress] entities.
 * @property solution The index (0-based) of the correct answer in the [answers] list.
 */
data class MultipleChoiceQuestion(
    val question: WordProgress,
    val answers: List<WordProgress>,
    val solution: Int
)
