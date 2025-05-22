package com.zyuniversita.domain.usecase.wordslist

import com.zyuniversita.domain.repository.QuizWordRepository
import javax.inject.Inject

interface StartFetchWordsByLanguageGeneralListUseCase {
    suspend operator fun invoke(lang: String): Unit
}

class StartFetchWordsByLanguageGeneralListUseCaseImpl @Inject constructor(private val quizWordRepository: QuizWordRepository) :
    StartFetchWordsByLanguageGeneralListUseCase {
    override suspend fun invoke(lang: String): Unit {
        quizWordRepository.fetchWordsByLanguage(lang)
    }

}