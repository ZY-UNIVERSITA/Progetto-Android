package com.zyuniversita.data.remote.authentication.api

import com.zyuniversita.data.remote.authentication.model.AuthenticationResponse
import com.zyuniversita.domain.model.authentication.LoginInfo
import com.zyuniversita.domain.model.authentication.RegistrationInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApi {
    @POST("users/register")
    suspend fun register(
        @Body userInfo: RegistrationInfo
    ): Response<AuthenticationResponse>

    @POST("users/login")
    suspend fun login(
        @Body userInfo: LoginInfo
    ): Response<AuthenticationResponse>

}