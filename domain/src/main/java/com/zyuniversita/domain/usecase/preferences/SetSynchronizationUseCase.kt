package com.zyuniversita.domain.usecase.preferences

import com.zyuniversita.domain.repository.PreferencesRepository
import javax.inject.Inject

interface SetSynchronizationUseCase {
    suspend operator fun invoke(status: Boolean): Unit
}

class SetSynchronizationUseCaseImpl @Inject constructor(private val preferencesRepository: PreferencesRepository): SetSynchronizationUseCase {
    override suspend fun invoke(status: Boolean) {
        preferencesRepository.changeSynchronization(status)
    }

}

