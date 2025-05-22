package com.zyuniversita.domain.usecase.preferences

import com.zyuniversita.domain.repository.PreferencesRepository
import javax.inject.Inject

interface SetWordRepetitionUseCase {
    suspend operator fun invoke(repetion: Boolean): Unit
}

class SetWordRepetitionUseCaseImpl @Inject constructor(private val preferencesRepository: PreferencesRepository): SetWordRepetitionUseCase {
    override suspend fun invoke(repetion: Boolean) {
        preferencesRepository.setWordRepetition(repetion)
    }

}