package com.zyuniversita.domain.usecase.synchronization

import com.zyuniversita.domain.model.synchronization.SynchronizationResponseInfo
import com.zyuniversita.domain.repository.SynchronizationRepository
import javax.inject.Inject

interface DownloadUserDataUseCase {
    suspend operator fun invoke(userId: Long): SynchronizationResponseInfo
}

class DownloadUserDataUseCaseImpl @Inject constructor(private val synchronizationRepository: SynchronizationRepository): DownloadUserDataUseCase {
    override suspend fun invoke(userId: Long): SynchronizationResponseInfo {
        return synchronizationRepository.downloadUserData(userId)
    }
}