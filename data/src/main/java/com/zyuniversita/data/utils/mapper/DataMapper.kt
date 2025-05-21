package com.zyuniversita.data.utils.mapper

import com.zyuniversita.data.local.database.entities.LanguageEntity
import com.zyuniversita.data.local.database.entities.WordEntity
import com.zyuniversita.data.local.database.relations.UserQuizPerformanceWithLanguage
import com.zyuniversita.data.local.database.relations.WordWithUserData
import com.zyuniversita.data.remote.wordDatabase.model.WordDatabaseEntity
import com.zyuniversita.domain.model.AvailableLanguage
import com.zyuniversita.domain.model.Word
import com.zyuniversita.domain.model.WordProgress
import com.zyuniversita.domain.model.userdata.GeneralUserStats
import javax.inject.Inject

/**
 * Interface responsible for mapping between different data models used across
 * various layers of the application (e.g., database entities, domain models, etc.).
 */
interface DataMapper {

    /**
     * Converts a [WordEntity] (typically from the local DB) to a domain-level [Word] object.
     *
     * @param entity The [WordEntity] to convert.
     * @return The corresponding [Word] object.
     */
    fun fromWordEntityToWordType(entity: WordEntity): Word

    /**
     * Converts a domain-level [Word] to a local [WordEntity] for database operations.
     *
     * @param entity The [Word] to convert.
     * @return The corresponding [WordEntity].
     */
    fun fromWordToWordEntityType(entity: Word): WordEntity

    /**
     * Maps a [WordWithUserData] (combined word and user interaction details) to a [WordProgress],
     * which is used to track user progress.
     *
     * @param entity The [WordWithUserData] containing user-specific data.
     * @return The corresponding [WordProgress] object.
     */
    fun fromWordWithUserDataToWordProgressType(entity: WordWithUserData): WordProgress

    /**
     * Converts a [WordDatabaseEntity] (from remote API or DB sync) to a [WordEntity].
     *
     * @param entity The [WordDatabaseEntity] to convert.
     * @return The corresponding [WordEntity].
     */
    fun fromWordDatabaseEntityToWordEntityType(entity: WordDatabaseEntity): WordEntity

    /**
     * Converts a [LanguageEntity] (usually from DB) to an [AvailableLanguage] domain model.
     *
     * @param entity The [LanguageEntity] to convert.
     * @return The corresponding [AvailableLanguage].
     */
    fun fromLanguageEntityToAvailableLanguage(entity: LanguageEntity): AvailableLanguage

    /**
     * Converts a [UserQuizPerformanceWithLanguage] (user performance + language data)
     * to a [GeneralUserStats] model for reporting or dashboard display.
     *
     * @param entity The [UserQuizPerformanceWithLanguage] to convert.
     * @return The corresponding [GeneralUserStats].
     */
    fun fromUserQuizPerformanceWithLanguageToGeneralUserStats(entity: UserQuizPerformanceWithLanguage): GeneralUserStats
}

/**
 * Default implementation of [DataMapper] that performs actual data transformations
 * between various data and domain model representations.
 */
class DataMapperImpl @Inject constructor() : DataMapper {

    /** {@inheritDoc} */
    override fun fromWordEntityToWordType(entity: WordEntity): Word = Word(
        wordId = entity.wordId,
        levelCode = entity.levelCode,
        languageCode = entity.languageCode,
        word = entity.word,
        meaning = entity.meaning,
        transliteration = entity.transliteration
    )

    /** {@inheritDoc} */
    override fun fromWordToWordEntityType(entity: Word): WordEntity = WordEntity(
        wordId = entity.wordId,
        levelCode = entity.levelCode,
        languageCode = entity.languageCode,
        word = entity.word,
        meaning = entity.meaning,
        transliteration = entity.transliteration
    )

    /** {@inheritDoc} */
    override fun fromWordWithUserDataToWordProgressType(entity: WordWithUserData): WordProgress =
        WordProgress(
            word = fromWordEntityToWordType(entity.word),
            selected = entity.userData?.selected ?: false,
            correct = entity.userData?.correctCount ?: 0,
            wrong = entity.userData?.wrongCount ?: 0,
            lastSeen = entity.userData?.lastSeen
        )

    /** {@inheritDoc} */
    override fun fromWordDatabaseEntityToWordEntityType(entity: WordDatabaseEntity): WordEntity =
        WordEntity(
            wordId = entity.wordId,
            levelCode = entity.levelCode,
            languageCode = entity.languageCode,
            word = entity.word,
            meaning = entity.meaning,
            transliteration = entity.transliteration
        )

    /** {@inheritDoc} */
    override fun fromLanguageEntityToAvailableLanguage(entity: LanguageEntity): AvailableLanguage =
        AvailableLanguage(
            code = entity.code,
            languageName = entity.name
        )

    /** {@inheritDoc} */
    override fun fromUserQuizPerformanceWithLanguageToGeneralUserStats(entity: UserQuizPerformanceWithLanguage): GeneralUserStats =
        GeneralUserStats(
            languageName = entity.userData.name,
            languageCode = entity.userData.code,
            completedQuiz = entity.word.completedQuiz,
            correctAnswer = entity.word.correctAnswer,
            wrongAnswer = entity.word.wrongAnswer
        )
}
