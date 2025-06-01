package com.zyuniversita.domain.usecase.authentication

import com.zyuniversita.domain.model.authentication.AuthenticationResponseInfo
import com.zyuniversita.domain.model.authentication.LoginInfo
import com.zyuniversita.domain.repository.AuthenticationRepository
import javax.inject.Inject

interface LoginUseCase: suspend (LoginInfo) -> AuthenticationResponseInfo

class LoginUseCaseImpl @Inject constructor(private val authenticationRepository: AuthenticationRepository): LoginUseCase {
    override suspend fun invoke(loginInfo: LoginInfo): AuthenticationResponseInfo {
        return authenticationRepository.login(loginInfo)
    }
}