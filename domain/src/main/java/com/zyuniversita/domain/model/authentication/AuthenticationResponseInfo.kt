package com.zyuniversita.domain.model.authentication

data class AuthenticationResponseInfo(
    val userId: Long,
    val username: String,
    val serverResponse: AuthenticationResponseEnum
)