package com.zyuniversita.data.repository

import android.content.Context
import com.zyuniversita.data.local.database.db.QuizDatabase
import com.zyuniversita.data.local.database.entities.LanguageEntity
import com.zyuniversita.data.local.jsonData.LanguageDataSource
import com.zyuniversita.data.utils.mapper.DataMapper
import com.zyuniversita.domain.model.AvailableLanguage
import com.zyuniversita.domain.repository.LanguageRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [LanguageRepository] that provides access to available languages
 * stored locally in the database.
 *
 * This repository fetches and exposes a list of [AvailableLanguage] using a state flow,
 * and uses a [DataMapper] for mapping between database and domain models.
 *
 * @param context Application context used for initializing the local database.
 * @param languageDataSource Optional future integration point for remote language fetching.
 * @param dataMapper Mapper used to convert [LanguageEntity] to [AvailableLanguage].
 */
class LanguageRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val languageDataSource: LanguageDataSource,
    private val dataMapper: DataMapper
) : LanguageRepository {

    // Internal mutable state flow for language data
    private val _languageDataList = MutableStateFlow<List<AvailableLanguage>>(listOf())

    /**
     * A [StateFlow] that emits the list of available languages as domain models ([AvailableLanguage]).
     */
    override val languageDataList: StateFlow<List<AvailableLanguage>> get() = _languageDataList

    private val languageDao = QuizDatabase.getInstance(context = context).languageDao()

    /**
     * Fetches the list of supported languages from the local database,
     * maps them to domain models using [DataMapper], and emits the result via [languageDataList].
     */
    override suspend fun fetchAvailableLanguage() {
        withContext(Dispatchers.IO) {
            val availableLanguage: List<LanguageEntity> = languageDao.getAllLanguage()

            val mappedLanguage: List<AvailableLanguage> = withContext(Dispatchers.Default) {
                availableLanguage.map(dataMapper::fromLanguageEntityToAvailableLanguage)
            }

            _languageDataList.emit(mappedLanguage)
        }
    }
}
