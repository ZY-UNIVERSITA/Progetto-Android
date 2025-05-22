package com.zyuniversita.domain.usecase.words

import com.zyuniversita.domain.model.Word
import com.zyuniversita.domain.repository.QuizWordRepository
import kotlinx.coroutines.flow.StateFlow

interface FetchWordsByLanguageAndLevelUseCase: () -> StateFlow<List<Word>>

class FetchWordsByLanguageAndLevelUseCaseImpl(private val quizWordRepository: QuizWordRepository): FetchWordsByLanguageAndLevelUseCase {
    override fun invoke(): StateFlow<List<Word>> {
        return quizWordRepository.wordsListByLevel
    }
}