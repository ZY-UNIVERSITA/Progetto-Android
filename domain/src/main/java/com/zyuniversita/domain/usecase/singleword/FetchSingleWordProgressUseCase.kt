package com.zyuniversita.domain.usecase.singleword

import com.zyuniversita.domain.model.words.WordProgress
import com.zyuniversita.domain.repository.QuizWordRepository
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

interface FetchSingleWordProgressUseCase : suspend () -> SharedFlow<WordProgress>

class FetchSingleWordProgressUseCaseImpl @Inject constructor(private val quizWordRepository: QuizWordRepository) :
    FetchSingleWordProgressUseCase {
    override suspend fun invoke(): SharedFlow<WordProgress> {
        return quizWordRepository.singleWordProgress
    }
}