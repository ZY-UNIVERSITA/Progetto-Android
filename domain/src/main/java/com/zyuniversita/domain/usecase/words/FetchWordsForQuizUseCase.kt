package com.zyuniversita.domain.usecase.words

import com.zyuniversita.domain.model.WordProgress
import com.zyuniversita.domain.repository.QuizWordRepository
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

interface FetchWordsForQuizUseCase: suspend () -> SharedFlow<List<WordProgress>>

class FetchWordsForQuizUseCaseImpl @Inject constructor(private val quizWordRepository: QuizWordRepository): FetchWordsForQuizUseCase {
    override suspend fun invoke(): SharedFlow<List<WordProgress>> {
        return quizWordRepository.wordsListForQuiz
    }

}