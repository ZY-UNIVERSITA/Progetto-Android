package com.zyuniversita.domain.usecase.preferences

import com.zyuniversita.domain.repository.PreferencesRepository
import javax.inject.Inject

interface SetCurrentDatabaseVersionUseCase: suspend (Int) -> Unit

class SetCurrentDatabaseVersionUseCaseImpl @Inject constructor(private val preferencesRepository: PreferencesRepository): SetCurrentDatabaseVersionUseCase {
    override suspend fun invoke(version: Int) {
        preferencesRepository.setDatabaseVersion(version)
    }

}