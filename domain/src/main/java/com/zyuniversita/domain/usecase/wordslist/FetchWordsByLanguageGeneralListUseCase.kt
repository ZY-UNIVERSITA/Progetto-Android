package com.zyuniversita.domain.usecase.wordslist

import com.zyuniversita.domain.model.words.Word
import com.zyuniversita.domain.repository.QuizWordRepository
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

interface FetchWordsByLanguageGeneralListUseCase {
    suspend operator fun invoke(): SharedFlow<List<Word>>
}

class FetchWordsByLanguageGeneralListUseCaseImpl @Inject constructor(private val quizWordRepository: QuizWordRepository) :
    FetchWordsByLanguageGeneralListUseCase {
    override suspend fun invoke(): SharedFlow<List<Word>> {
        return quizWordRepository.wordsByLanguageGeneralList
    }
}