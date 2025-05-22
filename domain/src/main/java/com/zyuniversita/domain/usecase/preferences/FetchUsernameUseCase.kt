package com.zyuniversita.domain.usecase.preferences

import com.zyuniversita.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FetchUsernameUseCase: () -> Flow<String?>

class FetchUsernameUseCaseImpl @Inject constructor(private val preferencesRepository: PreferencesRepository): FetchUsernameUseCase {
    override fun invoke(): Flow<String?> {
        return preferencesRepository.getUsername
    }
}