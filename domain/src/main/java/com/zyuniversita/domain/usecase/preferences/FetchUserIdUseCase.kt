package com.zyuniversita.domain.usecase.preferences

import com.zyuniversita.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FetchUserIdUseCase {
    suspend operator fun invoke(): Flow<Long?>
}

class FetchUserIdUseCaseImpl @Inject constructor(private val preferencesRepository: PreferencesRepository): FetchUserIdUseCase {
    override suspend fun invoke(): Flow<Long?> {
        return preferencesRepository.getUserId
    }
}