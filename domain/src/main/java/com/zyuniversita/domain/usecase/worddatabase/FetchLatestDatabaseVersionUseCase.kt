package com.zyuniversita.domain.usecase.worddatabase

import com.zyuniversita.domain.repository.QuizWordRepository
import javax.inject.Inject

interface FetchLatestDatabaseVersionUseCase: suspend () -> Int

class FetchLatestDatabaseVersionUseCaseImpl @Inject constructor(private val quizWordRepository: QuizWordRepository): FetchLatestDatabaseVersionUseCase {
    override suspend fun invoke(): Int {
        return quizWordRepository.fetchLatestWordDatabaseVersion()
    }
}