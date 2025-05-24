package com.zyuniversita.domain.usecase.languages

import com.zyuniversita.domain.model.AvailableLanguage
import com.zyuniversita.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface FetchLanguageUseCase: () -> StateFlow<List<AvailableLanguage>>

class FetchLanguageUseCaseImpl @Inject constructor(private val languageRepository: LanguageRepository):
    FetchLanguageUseCase {
    override fun invoke(): StateFlow<List<AvailableLanguage>> {
        return languageRepository.languageDataList
    }
}
