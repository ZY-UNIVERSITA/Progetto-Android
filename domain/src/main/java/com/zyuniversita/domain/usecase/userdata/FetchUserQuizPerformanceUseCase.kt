package com.zyuniversita.domain.usecase.userdata

import com.zyuniversita.domain.model.userdata.UserQuizPerformanceStats
import com.zyuniversita.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FetchUserQuizPerformanceUseCase {
    suspend operator fun invoke(): Flow<List<UserQuizPerformanceStats>>
}

class FetchUserQuizPerformanceUseCaseImpl @Inject constructor(private val userDataRepository: UserDataRepository): FetchUserQuizPerformanceUseCase {
    override suspend fun invoke(): Flow<List<UserQuizPerformanceStats>> {
        return userDataRepository.userQuizPerformance
    }

}