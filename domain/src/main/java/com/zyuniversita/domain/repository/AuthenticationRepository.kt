package com.zyuniversita.domain.repository

import com.zyuniversita.domain.model.authentication.AuthenticationResponseInfo
import com.zyuniversita.domain.model.authentication.LoginInfo
import com.zyuniversita.domain.model.authentication.RegistrationInfo

interface AuthenticationRepository {
    suspend fun register(registrationInfo: RegistrationInfo): AuthenticationResponseInfo
    suspend fun login(loginInfo: LoginInfo): AuthenticationResponseInfo
}