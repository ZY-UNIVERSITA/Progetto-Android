package com.zyuniversita.domain.usecase.words

import com.zyuniversita.domain.model.WordProgress
import com.zyuniversita.domain.repository.QuizWordRepository
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

interface FetchWordsAndUserDataByLanguageUseCase: suspend () -> SharedFlow<List<WordProgress>>

class FetchWordsAndUserDataByLanguageUseCaseImpl @Inject constructor(private val quizWordRepository: QuizWordRepository): FetchWordsAndUserDataByLanguageUseCase {
    override suspend fun invoke(): SharedFlow<List<WordProgress>> {
        return quizWordRepository.wordsListByLanguage
    }

}