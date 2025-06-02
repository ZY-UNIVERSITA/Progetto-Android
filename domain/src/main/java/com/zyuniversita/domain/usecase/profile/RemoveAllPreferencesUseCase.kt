package com.zyuniversita.domain.usecase.profile

import com.zyuniversita.domain.repository.PreferencesRepository
import javax.inject.Inject

interface RemoveAllPreferencesUseCase {
    suspend operator fun invoke(): Unit
}

class RemoveAllPreferencesUseCaseImpl @Inject constructor(private val preferencesRepository: PreferencesRepository): RemoveAllPreferencesUseCase {
    override suspend fun invoke() {
        preferencesRepository.resetPreferences()
    }

}