package com.zyuniversita.data.remote.synchronization.api

import com.zyuniversita.data.remote.synchronization.model.DownloadedUserData
import com.zyuniversita.domain.model.synchronization.UserDataToSynchronize
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SynchronizationApi {
    @POST("/users/save")
    suspend fun uploadUserData(
        @Body userInfo: UserDataToSynchronize
    ): Response<Boolean>

    @GET("/users/download")
    suspend fun downloadUserData(
        @Query("userInfo") userInfo: Long
    ): Response<DownloadedUserData>

}