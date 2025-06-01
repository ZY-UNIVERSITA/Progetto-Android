package com.zyuniversita.data.repository

import com.zyuniversita.data.remote.authentication.RemoteAuthenticationDataSource
import com.zyuniversita.domain.model.authentication.AuthenticationResponseInfo
import com.zyuniversita.domain.model.authentication.LoginInfo
import com.zyuniversita.domain.model.authentication.RegistrationInfo
import com.zyuniversita.domain.repository.AuthenticationRepository
import javax.inject.Inject
import kotlin.math.log

class AuthenticationRepositoryImpl @Inject constructor(private val remoteAuthenticationDataSource: RemoteAuthenticationDataSource) :
    AuthenticationRepository {

    override suspend fun register(registrationInfo: RegistrationInfo): AuthenticationResponseInfo {
        return remoteAuthenticationDataSource.register(registrationInfo)
    }

    override suspend fun login(loginInfo: LoginInfo): AuthenticationResponseInfo {
        return remoteAuthenticationDataSource.login(loginInfo = loginInfo)
    }
}