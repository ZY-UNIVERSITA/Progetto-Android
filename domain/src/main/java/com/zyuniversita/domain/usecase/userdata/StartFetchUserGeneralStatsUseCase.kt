package com.zyuniversita.domain.usecase.userdata

import com.zyuniversita.domain.repository.UserDataRepository
import javax.inject.Inject

interface StartFetchUserGeneralStatsUseCase {
    suspend operator fun invoke(userId: Long): Unit
}

class StartFetchUserGeneralStatsUseCaseImpl @Inject constructor(private val userDataRepository: UserDataRepository): StartFetchUserGeneralStatsUseCase {
    override suspend fun invoke(userId: Long) {
        userDataRepository.getUserQuizPerformance(userId)
    }

}