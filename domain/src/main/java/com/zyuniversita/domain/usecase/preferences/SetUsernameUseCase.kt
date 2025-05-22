package com.zyuniversita.domain.usecase.preferences

import com.zyuniversita.domain.repository.PreferencesRepository
import javax.inject.Inject

interface SetUsernameUseCase : suspend (String) -> Unit

class SetUsernameUseCaseImpl @Inject constructor(private val preferencesRepository: PreferencesRepository) :
    SetUsernameUseCase {
    override suspend fun invoke(username: String) {
        preferencesRepository.setUsername(username)
    }

}