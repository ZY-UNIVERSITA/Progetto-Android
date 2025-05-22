package com.zyuniversita.domain.repository

import com.zyuniversita.domain.model.AvailableLanguage
import kotlinx.coroutines.flow.StateFlow

interface LanguageRepository {
    val languageDataList: StateFlow<List<AvailableLanguage>>

    suspend fun fetchAvailableLanguage()
}