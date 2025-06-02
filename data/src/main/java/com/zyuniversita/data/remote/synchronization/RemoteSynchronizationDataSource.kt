package com.zyuniversita.data.remote.synchronization

import android.util.Log
import com.zyuniversita.data.remote.synchronization.api.SynchronizationApi
import com.zyuniversita.data.utils.mapper.DataMapper
import com.zyuniversita.domain.model.synchronization.SynchronizationResponseEnum
import com.zyuniversita.domain.model.synchronization.SynchronizationResponseInfo
import com.zyuniversita.domain.model.synchronization.UserDataToSynchronize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RemoteSynchronizationDataSource {
    suspend fun uploadData(userDataToSynchronize: UserDataToSynchronize)
    suspend fun downloadUserData(userId: Long): SynchronizationResponseInfo
}

class RemoteSynchronizationDataSourceImpl @Inject constructor(
    private val synchronizationApi: SynchronizationApi,
    private val dataMapper: DataMapper,
) : RemoteSynchronizationDataSource {
    companion object {
        private const val TAG = "Synchronization TAG"
    }

    override suspend fun uploadData(userDataToSynchronize: UserDataToSynchronize) {
        try {
            synchronizationApi.uploadUserData(userDataToSynchronize)
        } catch (e: Exception) {
            Log.e(TAG, "Connection error or server error.")
        }
    }

    override suspend fun downloadUserData(userId: Long): SynchronizationResponseInfo {
        try {
            val response = withContext(Dispatchers.IO) {
                synchronizationApi.downloadUserData(userId)
            }

            val responseInfo = response.body()?.let { responseBody ->
                dataMapper.fromDownloadedUserDataToSynchronizationResponseInfo(responseBody)
            } ?: SynchronizationResponseInfo(
                UserDataToSynchronize(-1, listOf(), listOf()),
                SynchronizationResponseEnum.ERROR
            )

            return responseInfo
        } catch (e: Exception) {
            Log.e(TAG, "Connection error or server error.")
            return SynchronizationResponseInfo(
                UserDataToSynchronize(-1, listOf(), listOf()),
                SynchronizationResponseEnum.CONNECTION_ERROR
            )
        }
    }
}