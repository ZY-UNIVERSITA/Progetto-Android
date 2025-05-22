package com.zyuniversita.domain.usecase.preferences

import com.zyuniversita.domain.repository.PreferencesRepository
import javax.inject.Inject

interface SetUserIdUseCase {
    suspend operator fun invoke(userId: Long): Unit
}

class SetUserIdUseCaseImpl @Inject constructor(private val preferencesRepository: PreferencesRepository): SetUserIdUseCase {
    override suspend fun invoke(userId: Long) {
        preferencesRepository.setUserId(userId)
    }

}