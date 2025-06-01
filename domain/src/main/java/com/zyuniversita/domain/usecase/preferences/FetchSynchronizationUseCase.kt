package com.zyuniversita.domain.usecase.preferences

import com.zyuniversita.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FetchSynchronizationUseCase {
    operator fun invoke(): Flow<Boolean>
}

class FetchSynchronizationUseCaseImpl @Inject constructor(private val preferencesRepository: PreferencesRepository): FetchSynchronizationUseCase {
    override fun invoke(): Flow<Boolean> {
        return preferencesRepository.hasSynchronization
    }
}