package com.zyuniversita.ui.setup.uistate

// Sealed -> Everything need to be defined in this file
sealed interface SetupEvent {
    // Singleton
    data object NavigateToHome : SetupEvent
    data object NavigateToRegister: SetupEvent
    data object NavigateToLocalRegister: SetupEvent
    data object NavigateToLogin: SetupEvent
    // to pass a data
    // data class example(val data: Int): SetupEvent
}