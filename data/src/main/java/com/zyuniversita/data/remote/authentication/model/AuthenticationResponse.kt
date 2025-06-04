package com.zyuniversita.data.remote.authentication.model

import com.squareup.moshi.JsonClass
import com.zyuniversita.domain.model.authentication.AuthenticationResponseEnum

@JsonClass(generateAdapter = true)
data class AuthenticationResponse(
    val userId: Long,
    val username: String,
    val serverResponse: AuthenticationResponseEnum
)