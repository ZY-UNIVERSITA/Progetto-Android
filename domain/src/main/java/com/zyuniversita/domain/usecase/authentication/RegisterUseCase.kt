package com.zyuniversita.domain.usecase.authentication

import com.zyuniversita.domain.model.authentication.AuthenticationResponseInfo
import com.zyuniversita.domain.model.authentication.RegistrationInfo
import com.zyuniversita.domain.repository.AuthenticationRepository
import javax.inject.Inject

interface RegisterUseCase: suspend (RegistrationInfo) -> AuthenticationResponseInfo

class RegisterUseCaseImpl @Inject constructor(private val authenticationRepository: AuthenticationRepository): RegisterUseCase {

    override suspend fun invoke(registrationInfo: RegistrationInfo): AuthenticationResponseInfo {
        return authenticationRepository.register(registrationInfo)
    }

}