package com.zyuniversita.domain.repository

import com.zyuniversita.domain.model.synchronization.SynchronizationResponseInfo
import com.zyuniversita.domain.model.synchronization.UserDataToSynchronize

interface SynchronizationRepository {
    suspend fun uploadUserData(userDataToSynchronize: UserDataToSynchronize)
    suspend fun downloadUserData(userId: Long): SynchronizationResponseInfo
}