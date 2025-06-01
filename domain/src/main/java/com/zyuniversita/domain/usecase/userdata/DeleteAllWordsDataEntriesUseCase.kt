package com.zyuniversita.domain.usecase.userdata

import com.zyuniversita.domain.repository.UserDataRepository
import javax.inject.Inject

interface DeleteAllWordsDataEntriesUseCase {
    suspend operator fun invoke(userId: Long)
}

class DeleteAllWordsDataEntriesUseCaseImpl @Inject constructor(private val userDataRepository: UserDataRepository) :
    DeleteAllWordsDataEntriesUseCase {
    override suspend fun invoke(userId: Long) {
        userDataRepository.deleteWordsDataByUserID(userId)
    }

}