package com.zyuniversita.domain.repository

import com.zyuniversita.domain.model.Word
import com.zyuniversita.domain.model.WordProgress
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface QuizWordRepository {
    suspend fun insertWords(words: List<Word>)

    fun wordsByLanguageAndLevel(lang: String, level: String)
    suspend fun fetchWordsWithUserData(lang: String)
    fun wordsWithUserData(lang: String, level: String)
    suspend fun selectedForQuiz(lang: String)

    suspend fun fetchWordsByLanguage(lang: String)

    suspend fun fetchSingleWordWithUserData(wordId: Int)

    /* Update del database */
    suspend fun fetchLatestWordDatabaseVersion(): Int
    fun updateWordDatabase(version: Int)

    // Observer
    val wordsListByLevel: StateFlow<List<Word>>

    val wordsListByLanguage: SharedFlow<List<WordProgress>>

    val wordsListForQuiz: SharedFlow<List<WordProgress>>

    val wordDatabaseVersion: SharedFlow<Boolean>

    val wordsByLanguageGeneralList: SharedFlow<List<Word>>

    val singleWordProgress: SharedFlow<WordProgress>
}