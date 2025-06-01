package com.zyuniversita.domain.usecase.synchronization

import com.zyuniversita.domain.model.synchronization.UserDataToSynchronize
import com.zyuniversita.domain.repository.SynchronizationRepository
import javax.inject.Inject

interface UploadUserData {
    suspend operator fun invoke(userDataToSynchronize: UserDataToSynchronize): Unit
}

class UploadUserDataImpl @Inject constructor(private val synchronizationRepository: SynchronizationRepository) :
    UploadUserData {
    override suspend fun invoke(userDataToSynchronize: UserDataToSynchronize) {
        synchronizationRepository.uploadUserData(userDataToSynchronize)
    }

}