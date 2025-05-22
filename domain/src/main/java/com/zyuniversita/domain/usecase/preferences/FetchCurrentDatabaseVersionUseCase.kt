package com.zyuniversita.domain.usecase.preferences

import com.zyuniversita.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FetchCurrentDatabaseVersionUseCase: () -> Flow<Int>

class FetchCurrentDatabaseVersionUseCaseImpl @Inject constructor(private val preferencesRepository: PreferencesRepository) : FetchCurrentDatabaseVersionUseCase {
    override fun invoke(): Flow<Int> {
        return preferencesRepository.getDatabaseVersion
    }

}