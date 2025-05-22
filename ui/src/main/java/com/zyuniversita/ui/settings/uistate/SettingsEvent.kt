package com.zyuniversita.ui.settings.uistate

sealed interface SettingsEvent {
    data object goToHome: SettingsEvent
}