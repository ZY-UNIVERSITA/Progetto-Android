package com.zyuniversita.domain.model.synchronization

data class SynchronizationResponseInfo(
    val data: UserDataToSynchronize,
    val response: SynchronizationResponseEnum
)
