package com.zyuniversita.data.repository

import com.zyuniversita.data.remote.synchronization.RemoteSynchronizationDataSource
import com.zyuniversita.domain.model.synchronization.SynchronizationResponseInfo
import com.zyuniversita.domain.model.synchronization.UserDataToSynchronize
import com.zyuniversita.domain.repository.SynchronizationRepository
import javax.inject.Inject

class SynchronizationRepositoryImpl @Inject constructor(private val remoteSynchronizationDataSource: RemoteSynchronizationDataSource) :
    SynchronizationRepository {
    override suspend fun uploadUserData(userDataToSynchronize: UserDataToSynchronize) {
        remoteSynchronizationDataSource.uploadData(userDataToSynchronize)
    }

    override suspend fun downloadUserData(userId: Long): SynchronizationResponseInfo {
        return remoteSynchronizationDataSource.downloadUserData(userId)
    }

}