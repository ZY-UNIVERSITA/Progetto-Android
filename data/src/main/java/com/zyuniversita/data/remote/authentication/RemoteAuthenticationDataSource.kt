package com.zyuniversita.data.remote.authentication

import android.util.Log
import com.zyuniversita.data.remote.authentication.api.AuthenticationApi
import com.zyuniversita.data.utils.mapper.DataMapper
import com.zyuniversita.domain.model.authentication.AuthenticationResponseEnum
import com.zyuniversita.domain.model.authentication.AuthenticationResponseInfo
import com.zyuniversita.domain.model.authentication.LoginInfo
import com.zyuniversita.domain.model.authentication.RegistrationInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RemoteAuthenticationDataSource {
    suspend fun register(registerInfo: RegistrationInfo): AuthenticationResponseInfo
    suspend fun login(loginInfo: LoginInfo): AuthenticationResponseInfo
}

class RemoteAuthenticationDataSourceImpl @Inject constructor(
    private val authenticationApi: AuthenticationApi,
    private val dataMapper: DataMapper,
) :
    RemoteAuthenticationDataSource {

    companion object {
        private const val TAG: String = "Authentication TAG"
    }

    override suspend fun register(registerInfo: RegistrationInfo): AuthenticationResponseInfo {
        try {
            val response = withContext(Dispatchers.IO) {
                authenticationApi.register(registerInfo)
            }

            val responseInfo = response.body()?.let { responseBody ->
                dataMapper.fromAuthenticationResponseToAuthenticationResponseInfo(responseBody)
            } ?: AuthenticationResponseInfo(-1, "", AuthenticationResponseEnum.IDENTIFICATION_FAILURE)

            return responseInfo
        } catch (e: Exception) {
            Log.e(TAG, "Connection error or server error.")
            return AuthenticationResponseInfo(-1, "", AuthenticationResponseEnum.CONNECTION_ERROR)
        }
    }

    override suspend fun login(loginInfo: LoginInfo): AuthenticationResponseInfo {
        try {
            val response = withContext(Dispatchers.IO) {
                authenticationApi.login(userInfo = loginInfo)
            }

            val responseInfo = response.body()?.let { responseBody ->
                dataMapper.fromAuthenticationResponseToAuthenticationResponseInfo(responseBody)
            } ?: AuthenticationResponseInfo(-1, "", AuthenticationResponseEnum.IDENTIFICATION_FAILURE)

            return responseInfo
        } catch (e: Exception) {
            Log.e(TAG, "Connection error or server error.")
            return AuthenticationResponseInfo(-1, "", AuthenticationResponseEnum.CONNECTION_ERROR)
        }
    }

}