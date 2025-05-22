package com.zyuniversita.domain.usecase.words

import com.zyuniversita.domain.repository.QuizWordRepository
import javax.inject.Inject

interface StartFetchWordsForQuizUseCase: suspend (String) -> Unit

class StartFetchWordsForQuizUseCaseImpl @Inject constructor(private val quizWordRepository: QuizWordRepository): StartFetchWordsForQuizUseCase {
    override suspend fun invoke(lang: String) {
        quizWordRepository.selectedForQuiz(lang)
    }

}