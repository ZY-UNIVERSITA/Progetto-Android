package com.zyuniversita.domain.usecase.userdata

import com.zyuniversita.domain.repository.UserDataRepository
import javax.inject.Inject

interface UpdateUserPerformanceUseCase {
    suspend operator fun invoke(userId: Long, languageCode: String, correctAnswer: Int, wrongAnswer: Int)
}

class UpdateUserPerformanceUseCaseImpl @Inject constructor(private val userDataRepository: UserDataRepository) :
    UpdateUserPerformanceUseCase {
    override suspend fun invoke(
        userId: Long,
        languageCode: String,
        correctAnswer: Int,
        wrongAnswer: Int,
    ) {
        userDataRepository.updateNewUserPerformance(userId, languageCode, correctAnswer, wrongAnswer)
    }

}