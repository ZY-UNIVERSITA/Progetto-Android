package com.zyuniversita.ui.setup.uistate

sealed interface AuthEvent {
    data object ConnectionError: AuthEvent
    data object IdentificationError: AuthEvent
    data object AuthenticationSuccessful: AuthEvent
    data object DownloadSuccessful: AuthEvent
    data object LoginAuth: AuthEvent
    data object RegistrationAuth: AuthEvent
    data object LocalRegistrationAuth: AuthEvent
}