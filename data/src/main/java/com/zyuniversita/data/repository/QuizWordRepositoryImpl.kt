package com.zyuniversita.data.repository

import android.content.Context
import com.zyuniversita.data.local.database.db.QuizDatabase
import com.zyuniversita.data.local.database.entities.WordEntity
import com.zyuniversita.data.local.database.relations.WordWithUserData
import com.zyuniversita.data.remote.wordDatabase.RemoteWordDatabaseDataSource
import com.zyuniversita.data.utils.mapper.DataMapper
import com.zyuniversita.domain.model.Word
import com.zyuniversita.domain.model.WordProgress
import com.zyuniversita.domain.repository.QuizWordRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [QuizWordRepository] responsible for accessing and modifying word-related data
 * for quizzes, including remote synchronization and user progress tracking.
 *
 * This repository interacts with a local Room database ([QuizDatabase]) as well as
 * a remote source ([RemoteWordDatabaseDataSource]) to manage word data.
 * It uses [DataMapper] to convert between different data layers.
 *
 * @param context Application context used to initialize the Room database.
 * @param remoteWordDatabaseDataSource Remote source from which the word database can be fetched.
 * @param dataMapper Mapper used to convert between entities and domain models.
 */
class QuizWordRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val remoteWordDatabaseDataSource: RemoteWordDatabaseDataSource,
    private val dataMapper: DataMapper,
) : QuizWordRepository {

    private val wordsDao = QuizDatabase.getInstance(context = context).wordDao()
    private val scopeIO = CoroutineScope(Dispatchers.IO)

    private val _wordsListByLevel: MutableStateFlow<List<Word>> = MutableStateFlow(listOf())
    /**
     * Emits a list of words filtered by level and language.
     */
    override val wordsListByLevel: StateFlow<List<Word>> = _wordsListByLevel.asStateFlow()

    private val _wordsListByLanguage: MutableSharedFlow<List<WordProgress>> = MutableSharedFlow(1)
    /**
     * Emits a list of [WordProgress] representing words and the user’s progress for a specific language.
     */
    override val wordsListByLanguage: SharedFlow<List<WordProgress>> =
        _wordsListByLanguage.asSharedFlow()

    private val _wordsListForQuiz: MutableSharedFlow<List<WordProgress>> = MutableSharedFlow(1)
    /**
     * Emits a list of selected words (or fallback list) for a quiz session.
     */
    override val wordsListForQuiz: SharedFlow<List<WordProgress>> = _wordsListForQuiz.asSharedFlow()

    private val _wordDatabaseVersion: MutableSharedFlow<Boolean> = MutableSharedFlow(1)
    /**
     * Emits whether the local word database is up-to-date compared to the remote version.
     */
    override val wordDatabaseVersion: SharedFlow<Boolean> get() = _wordDatabaseVersion.asSharedFlow()

    private val _wordsByLanguageGeneralList: MutableSharedFlow<List<Word>> = MutableSharedFlow(1)
    /**
     * Emits a list of words for a given language without user data.
     */
    override val wordsByLanguageGeneralList: SharedFlow<List<Word>> =
        _wordsByLanguageGeneralList.asSharedFlow()

    private val _singleWordProgress: MutableSharedFlow<WordProgress> = MutableSharedFlow(1)
    /**
     * Emits details for a specific word enriched with user data.
     */
    override val singleWordProgress: SharedFlow<WordProgress> = _singleWordProgress.asSharedFlow()

    /**
     * Inserts a list of words into the database.
     *
     * @param words The list of [Word] objects to insert.
     */
    override suspend fun insertWords(words: List<Word>) {
        wordsDao.insertWords(words.map(dataMapper::fromWordToWordEntityType))
    }

    /**
     * Fetches words filtered by language and level, maps them to domain models,
     * and emits the result to [wordsListByLevel].
     *
     * @param lang The language code.
     * @param level The level code.
     */
    override fun wordsByLanguageAndLevel(lang: String, level: String) {
        scopeIO.launch {
            val wordEntityList: List<WordEntity> = wordsDao.wordsByLevelAndLanguage(lang, level)
            val wordList: List<Word> = withContext(Dispatchers.Default) {
                wordEntityList.map(dataMapper::fromWordEntityToWordType)
            }
            _wordsListByLevel.emit(wordList)
        }
    }

    /**
     * Fetches all words and user data for a specific language, converts it to [WordProgress],
     * and emits the result to [wordsListByLanguage].
     *
     * @param lang The language code.
     */
    override suspend fun fetchWordsWithUserData(lang: String) {
        withContext(Dispatchers.IO) {
            val wordWithUserDataList = wordsDao.wordsWithUserData(lang)
            val wordProgress = withContext(Dispatchers.Default) {
                wordWithUserDataList.map(dataMapper::fromWordWithUserDataToWordProgressType)
            }
            _wordsListByLanguage.emit(wordProgress)
        }
    }

    /**
     * Fetches words and user data filtered by language and level, emits the result to [wordsListByLanguage].
     *
     * @param lang The language code.
     * @param level The level code.
     */
    override fun wordsWithUserData(lang: String, level: String) {
        scopeIO.launch {
            val wordWithUserDataList = wordsDao.wordsWithUserData(lang, level)
            val wordProgress = withContext(Dispatchers.Default) {
                wordWithUserDataList.map(dataMapper::fromWordWithUserDataToWordProgressType)
            }
            _wordsListByLanguage.emit(wordProgress)
        }
    }

    /**
     * Retrieves a list of selected words for a quiz. If no words are selected, falls back to all available words with user data.
     * Emits the result to [wordsListForQuiz].
     *
     * @param lang The language code.
     */
    override suspend fun selectedForQuiz(lang: String) {
        withContext(Dispatchers.IO) {
            var wordWithUserDataList = wordsDao.selectedForQuiz(lang)

            // Fallback: fetch all words if none are manually selected
            if (wordWithUserDataList.isEmpty()) {
                wordWithUserDataList = wordsDao.wordsWithUserData(lang)
            }

            val wordSelected = withContext(Dispatchers.Default) {
                wordWithUserDataList.map(dataMapper::fromWordWithUserDataToWordProgressType)
            }

            _wordsListForQuiz.emit(wordSelected)
        }
    }

    /**
     * Fetches all words for a specific language (without user data),
     * maps them and emits to [wordsByLanguageGeneralList].
     *
     * @param lang The language code.
     */
    override suspend fun fetchWordsByLanguage(lang: String) {
        withContext(Dispatchers.IO) {
            val wordsEntityList = wordsDao.fetchWordsByLanguage(lang)
            val wordsList = withContext(Dispatchers.Default) {
                wordsEntityList.map(dataMapper::fromWordEntityToWordType)
            }
            _wordsByLanguageGeneralList.emit(wordsList)
        }
    }

    /**
     * Fetches a single word, enriched with user data, and emits it to [singleWordProgress].
     *
     * @param wordId The ID of the word to fetch.
     */
    override suspend fun fetchSingleWordWithUserData(wordId: Int) {
        withContext(Dispatchers.IO) {
            val word = wordsDao.fetchSingleWordWithUserData(wordId)
            val mappedWord = dataMapper.fromWordWithUserDataToWordProgressType(word)
            _singleWordProgress.emit(mappedWord)
        }
    }

    /**
     * Fetches the latest word database version available from the remote source.
     *
     * @return The version number as [Int].
     */
    override suspend fun fetchLatestWordDatabaseVersion(): Int {
        val databaseVersion = remoteWordDatabaseDataSource.fetchVersion()
        return databaseVersion
    }

    /**
     * Updates the word database by fetching the latest version from the remote source
     * and inserting it into the local database.
     *
     * @param version The version number to fetch and update to.
     */
    override fun updateWordDatabase(version: Int) {
        scopeIO.launch {
            val newDatabase = remoteWordDatabaseDataSource.fetchDatabase(version)
            wordsDao.insertWords(
                newDatabase.map(dataMapper::fromWordDatabaseEntityToWordEntityType)
            )
        }
    }
}
