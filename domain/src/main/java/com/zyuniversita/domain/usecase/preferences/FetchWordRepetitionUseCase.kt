package com.zyuniversita.domain.usecase.preferences

import com.zyuniversita.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FetchWordRepetitionUseCase: suspend () -> Flow<Boolean>

class FetchWordRepetitionUseCaseImpl @Inject constructor(private val preferencesRepository: PreferencesRepository): FetchWordRepetitionUseCase {
    override suspend fun invoke(): Flow<Boolean> {
        return preferencesRepository.getWordRepetition
    }

}
