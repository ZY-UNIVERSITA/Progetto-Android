package com.zyuniversita.domain.usecase.userdata

import com.zyuniversita.domain.repository.UserDataRepository
import javax.inject.Inject

interface StartFetchUserQuizPerformanceUseCase {
    suspend operator fun invoke(userId: Long)
}

class StartFetchUserQuizPerformanceUseCaseImpl @Inject constructor(private val userDataRepository: UserDataRepository): StartFetchUserQuizPerformanceUseCase {
    override suspend fun invoke(userId: Long) {
        userDataRepository.getUserDataQuizPerformanceByUserID(userId)
    }
}
