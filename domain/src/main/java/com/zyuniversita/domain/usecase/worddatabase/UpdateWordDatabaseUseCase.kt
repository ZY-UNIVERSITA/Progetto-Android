package com.zyuniversita.domain.usecase.worddatabase

import com.zyuniversita.domain.repository.QuizWordRepository
import javax.inject.Inject

interface UpdateWordDatabaseUseCase: (Int) -> Unit

class UpdateWordDatabaseUseCaseImpl @Inject constructor(private val quizWordRepository: QuizWordRepository): UpdateWordDatabaseUseCase {
    override fun invoke(version: Int) {
        quizWordRepository.updateWordDatabase(version)
    }

}