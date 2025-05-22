package com.zyuniversita.domain.usecase.singleword

import com.zyuniversita.domain.repository.QuizWordRepository
import javax.inject.Inject

interface StartFetchSingleWordProgressUseCase {
    suspend operator fun invoke(wordId: Int): Unit
}

class StartFetchSingleWordProgressUseCaseImpl @Inject constructor(private val quizWordRepository: QuizWordRepository) :
    StartFetchSingleWordProgressUseCase {
    override suspend fun invoke(wordId: Int) {
        quizWordRepository.fetchSingleWordWithUserData(wordId = wordId)
    }
}