package com.zyuniversita.domain.usecase.userdata

import com.zyuniversita.domain.model.userdata.UserQuizPerformanceStats
import com.zyuniversita.domain.repository.UserDataRepository
import javax.inject.Inject

interface InsertAllUserDataEntriesUseCase {
    suspend operator fun invoke(userId: Long, userData: List<UserQuizPerformanceStats>)
}

class InsertAllUserDataEntriesUseCaseImpl @Inject constructor(private val userDataRepository: UserDataRepository): InsertAllUserDataEntriesUseCase {
    override suspend fun invoke(userId: Long, userData: List<UserQuizPerformanceStats>) {
        userDataRepository.insertAllUserDataByUserID(userId, userData)
    }

}