package com.zyuniversita.domain.usecase.userdata

import com.zyuniversita.domain.repository.UserDataRepository
import javax.inject.Inject

interface DeleteAllUserDataEntriesUseCase {
    suspend operator fun invoke(userId: Long)
}

class DeleteAllUserDataEntriesUseCaseImpl @Inject constructor(private val userDataRepository: UserDataRepository): DeleteAllUserDataEntriesUseCase {
    override suspend fun invoke(userId: Long) {
        userDataRepository.deleteUserDataByUserID(userId)
    }

}