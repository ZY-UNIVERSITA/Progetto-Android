package com.zyuniversita.domain.usecase

import com.zyuniversita.domain.repository.LanguageRepository
import javax.inject.Inject

interface StartFetchLanguageUseCase: suspend () -> Unit

class StartFetchLanguageUseCaseImpl @Inject constructor(private val languageRepository: LanguageRepository): StartFetchLanguageUseCase {
    override suspend fun invoke(): Unit {
        languageRepository.fetchAvailableLanguage()
    }
}