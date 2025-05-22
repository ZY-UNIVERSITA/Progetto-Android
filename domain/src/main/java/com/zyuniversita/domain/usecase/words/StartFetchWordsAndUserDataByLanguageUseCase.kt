package com.zyuniversita.domain.usecase.words

import com.zyuniversita.domain.repository.QuizWordRepository
import javax.inject.Inject

interface StartFetchWordsAndUserDataByLanguageUseCase: suspend (String) -> Unit

class StartFetchWordsAndUserDataByLanguageUseCaseImpl @Inject constructor(private val quizWordRepository: QuizWordRepository): StartFetchWordsAndUserDataByLanguageUseCase {
    override suspend fun invoke(lang: String) {
        quizWordRepository.fetchWordsWithUserData(lang)
    }

}